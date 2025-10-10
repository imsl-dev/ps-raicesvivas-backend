package com.raicesvivas.backend.services;

import com.raicesvivas.backend.models.dtos.EventoResponseDto;
import com.raicesvivas.backend.models.dtos.Eventos.EventoRequestDto;
import com.raicesvivas.backend.models.entities.Evento;
import com.raicesvivas.backend.models.entities.Sponsor;
import com.raicesvivas.backend.models.entities.Usuario;
import com.raicesvivas.backend.models.entities.auxiliar.CuentaBancaria;
import com.raicesvivas.backend.models.entities.auxiliar.Provincia;
import com.raicesvivas.backend.repositories.EventoRepository;
import com.raicesvivas.backend.repositories.SponsorRepository;
import com.raicesvivas.backend.repositories.UsuarioRepository;
import com.raicesvivas.backend.repositories.auxiliar.CuentaBancariaRepository;
import com.raicesvivas.backend.repositories.auxiliar.ProvinciaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventoService {

    private final EventoRepository eventoRepository;
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
        if (!eventoRepository.existsById(id)) {
            throw new EntityNotFoundException("Evento con ID: " + id + " no encontrado");
        }
        eventoRepository.deleteById(id);
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
            dto.setOrganizadorNombre(evento.getOrganizador().getNombre() + " " + evento.getOrganizador().getApellido());
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
}