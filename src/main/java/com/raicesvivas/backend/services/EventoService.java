package com.raicesvivas.backend.services;

import com.raicesvivas.backend.models.dtos.EventoResponseDto;
import com.raicesvivas.backend.models.dtos.Eventos.EventoRequestDto;
import com.raicesvivas.backend.models.entities.Evento;
import com.raicesvivas.backend.models.entities.Inscripcion;
import com.raicesvivas.backend.models.entities.Sponsor;
import com.raicesvivas.backend.models.entities.Usuario;
import com.raicesvivas.backend.models.entities.auxiliar.CuentaBancaria;
import com.raicesvivas.backend.models.entities.auxiliar.Provincia;
import com.raicesvivas.backend.models.enums.EstadoEvento;
import com.raicesvivas.backend.models.enums.EstadoInscripcion;
import com.raicesvivas.backend.repositories.EventoRepository;
import com.raicesvivas.backend.repositories.InscripcionRepository;
import com.raicesvivas.backend.repositories.SponsorRepository;
import com.raicesvivas.backend.repositories.UsuarioRepository;
import com.raicesvivas.backend.repositories.auxiliar.CuentaBancariaRepository;
import com.raicesvivas.backend.repositories.auxiliar.ProvinciaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventoService {

    private final EventoRepository eventoRepository;
    private final InscripcionRepository inscripcionRepository;
    private final ProvinciaRepository provinciaRepository;
    private final UsuarioRepository usuarioRepository;
    private final SponsorRepository sponsorRepository;
    private final CuentaBancariaRepository cuentaBancariaRepository;

    // ====================================
    // MÉTODOS PÚBLICOS
    // ====================================

    public List<EventoResponseDto> getAllEventos() {
        return eventoRepository.findAll().stream()
                .map(this::convertirAResponse)
                .collect(Collectors.toList());
    }

    public List<EventoResponseDto> getAllEventosOrganizador(Integer idOrganizador) {
        return eventoRepository.findByOrganizadorId(idOrganizador).stream()
                .map(this::convertirAResponse)
                .collect(Collectors.toList());
    }

    public EventoResponseDto getEventoById(Integer id) {
        Evento evento = eventoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Evento con ID: " + id + " no encontrado"));
        return convertirAResponse(evento);
    }

    public EventoResponseDto saveEvento(EventoRequestDto dto) {
        Evento nuevoEvento = convertirAEntidad(dto);
        nuevoEvento.setId(null); // Asegurar que es nuevo
        Evento eventoGuardado = eventoRepository.save(nuevoEvento);
        return convertirAResponse(eventoGuardado);
    }

    public EventoResponseDto updateEvento(EventoRequestDto dto) {
        // Verificar que el evento existe
        Evento eventoExistente = eventoRepository.findById(dto.getId())
                .orElseThrow(() -> new EntityNotFoundException("Evento con ID: " + dto.getId() + " no encontrado"));

        // Convertir DTO a entidad (con las relaciones cargadas)
        Evento eventoActualizado = convertirAEntidad(dto);
        eventoActualizado.setId(dto.getId()); // Mantener el ID

        Evento eventoGuardado = eventoRepository.save(eventoActualizado);
        return convertirAResponse(eventoGuardado);
    }

    public void deleteEventoById(Integer id) {
        Evento evento = eventoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Evento con ID: " + id + " no encontrado"));
        evento.setEstado(EstadoEvento.CANCELADO);
        eventoRepository.save(evento);
    }

    public boolean validarInscripcionEvento(Integer usuarioId, Integer eventoId){
        Optional<Inscripcion> inscripcion = inscripcionRepository.findByUsuarioIdAndEventoId(usuarioId, eventoId);
        return inscripcion.isPresent();
    }

    public Inscripcion inscribirseEvento(Integer usuarioId, Integer eventoId) {
        if (validarInscripcionEvento(usuarioId, eventoId)) {
            throw new IllegalStateException("Ya te encuentras inscripto a este evento");
        }
        else {
            Inscripcion inscripcion = new Inscripcion();
            inscripcion.setEventoId(eventoId);
            inscripcion.setUsuarioId(usuarioId);
            inscripcion.setEstado(EstadoInscripcion.PENDIENTE);
            return inscripcionRepository.save(inscripcion);
        }
    }

    public Inscripcion modificarAsistenciaEvento(Integer usuarioId, Integer eventoId, EstadoInscripcion estado) {
        Optional<Inscripcion> inscripcionOpc = inscripcionRepository.findByUsuarioIdAndEventoId(usuarioId, eventoId);
        // Validamos que el evento exista
        if (inscripcionOpc.isPresent()) {
            // Gestiona inscripión y cancelación
            if (EstadoInscripcion.PENDIENTE.equals(estado) || EstadoInscripcion.CANCELADO.equals(estado)) {
                Inscripcion inscripcion = inscripcionOpc.get();
                inscripcion.setEstado(estado);
                return inscripcionRepository.save(inscripcion);
            }
            // Gestiona las asistencias y asignación de puntos
            else {
                return gestionarAsistencia(inscripcionOpc.get(), estado);
            }
        }
        else {
            throw new IllegalStateException("No te encuentras inscripto a este evento");}
    }

    // ====================================
    // MÉTODOS PRIVADOS DE CONVERSIÓN
    // ====================================

    /**
     * Convierte un EventoRequestDto a una entidad Evento
     * Carga todas las relaciones necesarias desde la base de datos
     */
    private Evento convertirAEntidad(EventoRequestDto dto) {
        Evento evento = new Evento();

        // Campos básicos
        evento.setId(dto.getId());
        evento.setTipo(dto.getTipo());
        evento.setEstado(dto.getEstado());
        evento.setNombre(dto.getNombre());
        evento.setDescripcion(dto.getDescripcion());
        evento.setRutaImg(dto.getRutaImg());
        evento.setDireccion(dto.getDireccion());
        evento.setLatitud(dto.getLatitud());
        evento.setLongitud(dto.getLongitud());
        evento.setHoraInicio(dto.getHoraInicio());
        evento.setHoraFin(dto.getHoraFin());
        evento.setPuntosAsistencia(dto.getPuntosAsistencia());
        evento.setCostoInterno(dto.getCostoInterno());
        evento.setCostoInscripcion(dto.getCostoInscripcion());

        // Cargar provincia (obligatoria)
        Provincia provincia = provinciaRepository.findById(dto.getProvinciaId())
                .orElseThrow(() -> new EntityNotFoundException("Provincia con ID: " + dto.getProvinciaId() + " no encontrada"));
        evento.setProvincia(provincia);

        // Cargar organizador (obligatorio)
        Usuario organizador = usuarioRepository.findById(dto.getOrganizadorId())
                .orElseThrow(() -> new EntityNotFoundException("Usuario con ID: " + dto.getOrganizadorId() + " no encontrado"));
        evento.setOrganizador(organizador);

        // Cargar sponsor (opcional)
        if (dto.getSponsorId() != null) {
            Sponsor sponsor = sponsorRepository.findById(dto.getSponsorId())
                    .orElseThrow(() -> new EntityNotFoundException("Sponsor con ID: " + dto.getSponsorId() + " no encontrado"));
            evento.setSponsor(sponsor);
        }

        // Cargar cuenta bancaria (opcional)
        if (dto.getCuentaBancariaId() != null) {
            CuentaBancaria cuentaBancaria = cuentaBancariaRepository.findById(dto.getCuentaBancariaId())
                    .orElseThrow(() -> new EntityNotFoundException("Cuenta bancaria con ID: " + dto.getCuentaBancariaId() + " no encontrada"));
            evento.setCuentaBancaria(cuentaBancaria);
        }

        return evento;
    }

    /**
     * Convierte una entidad Evento a EventoResponseDto
     * Extrae los IDs y nombres de las relaciones
     */
    private EventoResponseDto convertirAResponse(Evento evento) {
        EventoResponseDto dto = new EventoResponseDto();

        // Campos básicos
        dto.setId(evento.getId());
        dto.setTipo(evento.getTipo());
        dto.setEstado(evento.getEstado());
        dto.setNombre(evento.getNombre());
        dto.setDescripcion(evento.getDescripcion());
        dto.setRutaImg(evento.getRutaImg());
        dto.setDireccion(evento.getDireccion());
        dto.setLatitud(evento.getLatitud());
        dto.setLongitud(evento.getLongitud());
        dto.setHoraInicio(evento.getHoraInicio());
        dto.setHoraFin(evento.getHoraFin());
        dto.setPuntosAsistencia(evento.getPuntosAsistencia());
        dto.setCostoInterno(evento.getCostoInterno());
        dto.setCostoInscripcion(evento.getCostoInscripcion());

        // Extraer IDs y nombres de las relaciones
        if (evento.getProvincia() != null) {
            dto.setProvinciaId(evento.getProvincia().getId());
            dto.setProvinciaNombre(evento.getProvincia().getNombre());
        }

        if (evento.getOrganizador() != null) {
            dto.setOrganizadorId(evento.getOrganizador().getId());
            dto.setOrganizadorNombre(evento.getOrganizador().getNombre());
            dto.setOrganizadorApellido(evento.getOrganizador().getApellido());
            dto.setOrganizadorEmail(evento.getOrganizador().getEmail());
            dto.setOrganizadorRutaImg(evento.getOrganizador().getRutaImg());
        }

        if (evento.getSponsor() != null) {
            dto.setSponsorId(evento.getSponsor().getId());
            dto.setSponsorNombre(evento.getSponsor().getNombre());
        }

        if (evento.getCuentaBancaria() != null) {
            dto.setCuentaBancariaId(evento.getCuentaBancaria().getId());
        }

        return dto;
    }

    // Gestiona las asistencias y asignación de puntos
    @Transactional
    protected Inscripcion gestionarAsistencia(Inscripcion inscripcion, EstadoInscripcion asistencia){
        Optional<Evento> eventoOpc = eventoRepository.findById(inscripcion.getEventoId());
        Optional<Usuario> usuarioOpc = usuarioRepository.findById(inscripcion.getUsuarioId());
        if (eventoOpc.isPresent() && usuarioOpc.isPresent()) {
            Evento evento = eventoOpc.get();
            Usuario usuario = usuarioOpc.get();
            Integer puntosEvento = evento.getPuntosAsistencia() != null ? evento.getPuntosAsistencia() : 0;

            // ASIGNACIÓN DE PUNTOS POR ASISTENCIA
            if (EstadoInscripcion.PRESENTE.equals(asistencia)) {
                usuario.setPuntos(usuario.getPuntos() + puntosEvento);
                inscripcion.setEstado(asistencia);
                usuarioRepository.save(usuario);
                return inscripcionRepository.save(inscripcion);
            }

            // GESTIÓN DE AUSENTES
            else if (EstadoInscripcion.AUSENTE.equals(asistencia)) {
                // Flujo 1 - El usuario nunca asistió, por lo que no se asignaron puntos incorrectamente
                if (EstadoInscripcion.PENDIENTE.equals(inscripcion.getEstado())) {
                    inscripcion.setEstado(asistencia);
                    return inscripcionRepository.save(inscripcion);
                }

                // Flujo 2 - Se asignó presente de forma errónea y ahora se deben restar los puntos
                else if (EstadoInscripcion.PRESENTE.equals(inscripcion.getEstado())) {
                    usuario.setPuntos(usuario.getPuntos() - puntosEvento);
                    inscripcion.setEstado(asistencia);
                    usuarioRepository.save(usuario);
                    return inscripcionRepository.save(inscripcion);
                }
                else throw new IllegalStateException("El estado: " + inscripcion.getEstado() + " no puede ser modificado a: "+asistencia);
            }
            else throw new IllegalStateException("El estado: " + inscripcion.getEstado() + " no puede ser modificado a: "+asistencia);
        }
        else {
            throw new IllegalStateException("No se encuentra el evento o usuario. Evento ID: " + inscripcion.getEventoId() + " Usuario ID: " + inscripcion.getUsuarioId());}
    }
}