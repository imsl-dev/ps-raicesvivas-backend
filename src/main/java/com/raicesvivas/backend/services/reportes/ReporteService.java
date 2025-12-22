package com.raicesvivas.backend.services.reportes;

import com.raicesvivas.backend.models.dtos.reportes.*;
import com.raicesvivas.backend.models.entities.Evento;
import com.raicesvivas.backend.models.entities.Pago;
import com.raicesvivas.backend.models.enums.TipoEvento;
import com.raicesvivas.backend.repositories.EventoRepository;
import com.raicesvivas.backend.repositories.InscripcionRepository;
import com.raicesvivas.backend.repositories.pagos.PagoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
@Service
@RequiredArgsConstructor
@Slf4j
public class ReporteService {

    private final PagoRepository pagoRepository;
    private final InscripcionRepository inscripcionRepository;
    private final EventoRepository eventoRepository;

    /**
     * Obtiene los KPIs generales del dashboard
     */
    public ReporteKPIDto obtenerKPIs() {
        LocalDateTime now = LocalDateTime.now();
        int year = now.getYear();
        int month = now.getMonthValue();

        // KPI 1: Total recaudado en el mes (5% de donaciones)
        List<Pago> donacionesMes = pagoRepository.findDonacionesByYearAndMonth(year, month);
        BigDecimal totalRecaudado = donacionesMes.stream()
                .map(pago -> pago.getMonto().multiply(new BigDecimal("0.05")))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // KPI 2: Inscripciones del mes
        Long inscripcionesMes = inscripcionRepository.countByYearAndMonth(year, month);

        // KPI 3: Eventos activos
        Long eventosActivos = eventoRepository.countEventosActivos();

        log.info("KPIs obtenidos - Recaudado: {}, Inscripciones: {}, Eventos activos: {}",
                totalRecaudado, inscripcionesMes, eventosActivos);

        return new ReporteKPIDto(totalRecaudado, inscripcionesMes, eventosActivos);
    }

    /**
     * Obtiene el gráfico de torta de tipos de eventos más populares
     */
    public ReporteGraficoTortaDto obtenerTiposEventosPopulares() {
        List<Object[]> resultados = eventoRepository.findTiposEventosMasPopulares();

        List<String> labels = new ArrayList<>();
        List<Long> values = new ArrayList<>();

        for (Object[] resultado : resultados) {
            TipoEvento tipo = (TipoEvento) resultado[0];
            Long cantidad = (Long) resultado[1];

            labels.add(tipo.name());
            values.add(cantidad);
        }

        log.info("Tipos de eventos populares obtenidos: {} tipos", labels.size());
        return new ReporteGraficoTortaDto(labels, values);
    }

    /**
     * Obtiene el gráfico de recaudación mensual del año en curso
     */
    public ReporteGraficoMensualDto obtenerRecaudacionMensual() {
        int year = LocalDateTime.now().getYear();
        List<Object[]> resultados = pagoRepository.findRecaudacionMensualByYear(year);

        // Crear mapa con los datos obtenidos
        Map<Integer, BigDecimal> recaudacionPorMes = new HashMap<>();
        for (Object[] resultado : resultados) {
            Integer mes = (Integer) resultado[0];
            BigDecimal total = (BigDecimal) resultado[1];
            recaudacionPorMes.put(mes, total);
        }

        // Generar lista de todos los meses (1-12) con valores
        List<String> meses = IntStream.rangeClosed(1, 12)
                .mapToObj(mes -> {
                    Locale locale = new Locale("es", "ES");
                    return java.time.Month.of(mes)
                            .getDisplayName(TextStyle.SHORT, locale)
                            .toUpperCase();
                })
                .collect(Collectors.toList());

        List<BigDecimal> valores = IntStream.rangeClosed(1, 12)
                .mapToObj(mes -> recaudacionPorMes.getOrDefault(mes, BigDecimal.ZERO))
                .collect(Collectors.toList());

        log.info("Recaudación mensual obtenida para el año {}", year);
        return new ReporteGraficoMensualDto(meses, valores);
    }

    /**
     * Obtiene el gráfico de inscripciones mensuales del año en curso
     */
    public ReporteGraficoInscripcionesMensualDto obtenerInscripcionesMensuales() {
        int year = LocalDateTime.now().getYear();
        List<Object[]> resultados = inscripcionRepository.findInscripcionesMensualesByYear(year);

        // Crear mapa con los datos obtenidos
        Map<Integer, Long> inscripcionesPorMes = new HashMap<>();
        for (Object[] resultado : resultados) {
            Integer mes = (Integer) resultado[0];
            Long total = (Long) resultado[1];
            inscripcionesPorMes.put(mes, total);
        }

        // Generar lista de todos los meses (1-12) con valores
        List<String> meses = IntStream.rangeClosed(1, 12)
                .mapToObj(mes -> {
                    Locale locale = new Locale("es", "ES");
                    return java.time.Month.of(mes)
                            .getDisplayName(TextStyle.SHORT, locale)
                            .toUpperCase();
                })
                .collect(Collectors.toList());

        List<Long> valores = IntStream.rangeClosed(1, 12)
                .mapToObj(mes -> inscripcionesPorMes.getOrDefault(mes, 0L))
                .collect(Collectors.toList());

        log.info("Inscripciones mensuales obtenidas para el año {}", year);
        return new ReporteGraficoInscripcionesMensualDto(meses, valores);
    }

    /**
     * Obtiene el histórico de eventos con filtros y paginación
     */
    public Page<ReporteEventoDto> obtenerHistoricoEventos(
            String nombre,
            TipoEvento tipo,
            Boolean esGratuito,
            Pageable pageable) {

        Page<Evento> eventos = eventoRepository.findEventosConFiltros(nombre, tipo, esGratuito, pageable);

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

            // NUEVO: Costo interno
            dto.setCostoInterno(evento.getCostoInterno() != null ? evento.getCostoInterno() : BigDecimal.ZERO);

            // NUEVO: Sponsor
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
}