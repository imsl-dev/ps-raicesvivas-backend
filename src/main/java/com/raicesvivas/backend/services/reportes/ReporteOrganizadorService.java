package com.raicesvivas.backend.services.reportes;

import com.raicesvivas.backend.models.dtos.reportes.*;
import com.raicesvivas.backend.models.entities.Evento;
import com.raicesvivas.backend.models.entities.Inscripcion;
import com.raicesvivas.backend.models.entities.Pago;
import com.raicesvivas.backend.models.entities.Usuario;
import com.raicesvivas.backend.models.enums.EstadoEvento;
import com.raicesvivas.backend.models.enums.EstadoInscripcion;
import com.raicesvivas.backend.models.enums.TipoEvento;
import com.raicesvivas.backend.models.enums.pagos.EstadoPago;
import com.raicesvivas.backend.models.enums.pagos.TipoPago;
import com.raicesvivas.backend.repositories.EventoRepository;
import com.raicesvivas.backend.repositories.InscripcionRepository;
import com.raicesvivas.backend.repositories.UsuarioRepository;
import com.raicesvivas.backend.repositories.pagos.PagoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReporteOrganizadorService {

    private final EventoRepository eventoRepository;
    private final InscripcionRepository inscripcionRepository;
    private final PagoRepository pagoRepository;
    private final UsuarioRepository usuarioRepository;

    /**
     * Obtiene los KPIs del organizador:
     * - Total de eventos creados
     * - Promedio de asistencias por evento
     */
    public ReporteOrganizadorKPIDto obtenerKPIsOrganizador(Integer organizadorId) {
        List<Evento> eventosOrganizador = eventoRepository.findByOrganizadorId(organizadorId);

        // Total de eventos creados
        long totalEventos = eventosOrganizador.size();

        // Promedio de asistencias por evento (solo eventos finalizados)
        List<Evento> eventosFinalizados = eventosOrganizador.stream()
                .filter(e -> EstadoEvento.FINALIZADO.equals(e.getEstado()))
                .collect(Collectors.toList());

        BigDecimal promedioAsistencias = BigDecimal.ZERO;

        if (!eventosFinalizados.isEmpty()) {
            long totalPresentes = 0;
            for (Evento evento : eventosFinalizados) {
                long presentes = inscripcionRepository.countByEventoIdAndEstado(evento.getId(), EstadoInscripcion.PRESENTE);
                totalPresentes += presentes;
            }
            promedioAsistencias = BigDecimal.valueOf(totalPresentes)
                    .divide(BigDecimal.valueOf(eventosFinalizados.size()), 2, RoundingMode.HALF_UP);
        }

        log.info("KPIs Organizador {} - Total eventos: {}, Promedio asistencias: {}",
                organizadorId, totalEventos, promedioAsistencias);

        return new ReporteOrganizadorKPIDto(totalEventos, promedioAsistencias);
    }

    /**
     * Obtiene la tasa de asistencia para el gráfico de torta
     * Muestra Presentes vs Ausentes en eventos finalizados
     */
    public ReporteTasaAsistenciaDto obtenerTasaAsistencia(Integer organizadorId) {
        List<Evento> eventosFinalizados = eventoRepository.findByOrganizadorId(organizadorId).stream()
                .filter(e -> EstadoEvento.FINALIZADO.equals(e.getEstado()))
                .collect(Collectors.toList());

        long totalPresentes = 0;
        long totalAusentes = 0;

        for (Evento evento : eventosFinalizados) {
            totalPresentes += inscripcionRepository.countByEventoIdAndEstado(evento.getId(), EstadoInscripcion.PRESENTE);
            totalAusentes += inscripcionRepository.countByEventoIdAndEstado(evento.getId(), EstadoInscripcion.AUSENTE);
        }

        long totalInscripciones = totalPresentes + totalAusentes;
        double porcentajeAsistencia = totalInscripciones > 0
                ? (double) totalPresentes / totalInscripciones * 100
                : 0.0;

        List<String> labels = Arrays.asList("Presentes", "Ausentes");
        List<Long> values = Arrays.asList(totalPresentes, totalAusentes);

        log.info("Tasa asistencia Organizador {} - Presentes: {}, Ausentes: {}, %: {}",
                organizadorId, totalPresentes, totalAusentes, porcentajeAsistencia);

        return new ReporteTasaAsistenciaDto(labels, values, totalInscripciones,
                Math.round(porcentajeAsistencia * 100.0) / 100.0);
    }

    /**
     * Obtiene la recaudación neta por evento
     * Recaudación neta = Total donaciones + Total inscripciones - Costo interno
     */
    public ReporteRecaudacionNetaEventoDto obtenerRecaudacionNetaPorEvento(Integer organizadorId) {
        List<Evento> eventosOrganizador = eventoRepository.findByOrganizadorId(organizadorId);

        List<String> nombresEventos = new ArrayList<>();
        List<BigDecimal> recaudacionNeta = new ArrayList<>();

        for (Evento evento : eventosOrganizador) {
            // Obtener total de donaciones aprobadas para este evento
            BigDecimal totalDonaciones = pagoRepository.findByEventoId(evento.getId()).stream()
                    .filter(p -> TipoPago.DONACION.equals(p.getTipoPago()) && EstadoPago.APROBADO.equals(p.getEstadoPago()))
                    .map(Pago::getMonto)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            // Obtener total de inscripciones aprobadas para este evento
            BigDecimal totalInscripciones = pagoRepository.findByEventoId(evento.getId()).stream()
                    .filter(p -> TipoPago.INSCRIPCION.equals(p.getTipoPago()) && EstadoPago.APROBADO.equals(p.getEstadoPago()))
                    .map(Pago::getMonto)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            // Calcular neto: donaciones + inscripciones - costo interno
            BigDecimal costoInterno = evento.getCostoInterno() != null ? evento.getCostoInterno() : BigDecimal.ZERO;
            BigDecimal neto = totalDonaciones.add(totalInscripciones).subtract(costoInterno);

            nombresEventos.add(evento.getNombre());
            recaudacionNeta.add(neto);
        }

        log.info("Recaudación neta calculada para {} eventos del organizador {}",
                nombresEventos.size(), organizadorId);

        return new ReporteRecaudacionNetaEventoDto(nombresEventos, recaudacionNeta);
    }

    /**
     * Obtiene las donaciones de los eventos del organizador
     */
    public List<ReporteDonacionOrganizadorDto> obtenerDonacionesOrganizador(Integer organizadorId) {
        List<Evento> eventosOrganizador = eventoRepository.findByOrganizadorId(organizadorId);
        List<Integer> eventosIds = eventosOrganizador.stream()
                .map(Evento::getId)
                .collect(Collectors.toList());

        if (eventosIds.isEmpty()) {
            return Collections.emptyList();
        }

        // Crear un mapa de eventos para acceso rápido
        Map<Integer, String> eventosMap = eventosOrganizador.stream()
                .collect(Collectors.toMap(Evento::getId, Evento::getNombre));

        List<ReporteDonacionOrganizadorDto> donaciones = new ArrayList<>();

        for (Integer eventoId : eventosIds) {
            List<Pago> pagosEvento = pagoRepository.findByEventoId(eventoId).stream()
                    .filter(p -> TipoPago.DONACION.equals(p.getTipoPago()) && EstadoPago.APROBADO.equals(p.getEstadoPago()))
                    .sorted(Comparator.comparing(Pago::getFechaCreacion).reversed())
                    .collect(Collectors.toList());

            for (Pago pago : pagosEvento) {
                String nombreUsuario = usuarioRepository.findById(pago.getUsuarioId())
                        .map(u -> u.getNombre() + " " + u.getApellido())
                        .orElse("Usuario desconocido");

                donaciones.add(new ReporteDonacionOrganizadorDto(
                        pago.getId(),
                        eventosMap.get(eventoId),
                        pago.getFechaCreacion(),
                        nombreUsuario,
                        pago.getMonto(),
                        pago.getMensaje()
                ));
            }
        }

        // Ordenar por fecha descendente
        donaciones.sort(Comparator.comparing(ReporteDonacionOrganizadorDto::getFechaHora).reversed());

        log.info("Obtenidas {} donaciones para organizador {}", donaciones.size(), organizadorId);

        return donaciones;
    }

    /**
     * Obtiene el histórico de eventos del organizador con filtros y paginación
     */
    public Page<ReporteEventoDto> obtenerHistoricoEventosOrganizador(
            Integer organizadorId,
            String nombre,
            TipoEvento tipo,
            Boolean esGratuito,
            Pageable pageable) {

        Page<Evento> eventos = eventoRepository.findEventosOrganizadorConFiltros(
                organizadorId, nombre, tipo, esGratuito, pageable);

        return eventos.map(evento -> {
            ReporteEventoDto dto = new ReporteEventoDto();
            dto.setId(evento.getId());
            dto.setNombre(evento.getNombre());
            dto.setTipo(evento.getTipo());
            dto.setHoraInicio(evento.getHoraInicio());

            // Ubicación
            String ubicacion = evento.getDireccion() != null ? evento.getDireccion() : "";
            if (evento.getProvincia() != null) {
                ubicacion += (ubicacion.isEmpty() ? "" : ", ") + evento.getProvincia().getNombre();
            }
            dto.setUbicacion(ubicacion);

            dto.setEstado(evento.getEstado());
            dto.setCostoInscripcion(evento.getCostoInscripcion() != null ? evento.getCostoInscripcion() : BigDecimal.ZERO);
            dto.setCostoInterno(evento.getCostoInterno() != null ? evento.getCostoInterno() : BigDecimal.ZERO);

            // Sponsor
            if (evento.getSponsor() != null) {
                dto.setSponsorNombre(evento.getSponsor().getNombre());
            } else {
                dto.setSponsorNombre("Sin sponsor");
            }

            // Contar inscripciones
            Long totalInscripciones = inscripcionRepository.countByEventoId(evento.getId());
            dto.setTotalInscripciones(totalInscripciones);

            return dto;
        });
    }

    /**
     * Obtiene todos los eventos del organizador para exportar
     */
    public List<ReporteEventoDto> obtenerEventosOrganizadorParaExportar(
            Integer organizadorId,
            String nombre,
            TipoEvento tipo,
            Boolean esGratuito) {

        List<Evento> eventos = eventoRepository.findByOrganizadorId(organizadorId);

        // Aplicar filtros
        return eventos.stream()
                .filter(e -> nombre == null || nombre.isEmpty() ||
                        e.getNombre().toLowerCase().contains(nombre.toLowerCase()))
                .filter(e -> tipo == null || e.getTipo().equals(tipo))
                .filter(e -> esGratuito == null ||
                        (esGratuito && (e.getCostoInscripcion() == null || e.getCostoInscripcion().compareTo(BigDecimal.ZERO) == 0)) ||
                        (!esGratuito && e.getCostoInscripcion() != null && e.getCostoInscripcion().compareTo(BigDecimal.ZERO) > 0))
                .map(evento -> {
                    ReporteEventoDto dto = new ReporteEventoDto();
                    dto.setId(evento.getId());
                    dto.setNombre(evento.getNombre());
                    dto.setTipo(evento.getTipo());
                    dto.setHoraInicio(evento.getHoraInicio());

                    String ubicacion = evento.getDireccion() != null ? evento.getDireccion() : "";
                    if (evento.getProvincia() != null) {
                        ubicacion += (ubicacion.isEmpty() ? "" : ", ") + evento.getProvincia().getNombre();
                    }
                    dto.setUbicacion(ubicacion);

                    dto.setEstado(evento.getEstado());
                    dto.setCostoInscripcion(evento.getCostoInscripcion() != null ? evento.getCostoInscripcion() : BigDecimal.ZERO);
                    dto.setCostoInterno(evento.getCostoInterno() != null ? evento.getCostoInterno() : BigDecimal.ZERO);

                    if (evento.getSponsor() != null) {
                        dto.setSponsorNombre(evento.getSponsor().getNombre());
                    } else {
                        dto.setSponsorNombre("Sin sponsor");
                    }

                    Long totalInscripciones = inscripcionRepository.countByEventoId(evento.getId());
                    dto.setTotalInscripciones(totalInscripciones);

                    return dto;
                })
                .sorted(Comparator.comparing(ReporteEventoDto::getHoraInicio).reversed())
                .collect(Collectors.toList());
    }
}