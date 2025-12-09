package com.raicesvivas.backend.controllers.reportes;

import com.raicesvivas.backend.models.dtos.reportes.*;
import com.raicesvivas.backend.models.enums.TipoEvento;
import com.raicesvivas.backend.services.reportes.ReporteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reportes")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "http://localhost:4200")
public class ReporteController {

    private final ReporteService reporteService;

    @GetMapping("/kpis")
    public ResponseEntity<ReporteKPIDto> obtenerKPIs() {
        log.info("Solicitud de KPIs recibida");
        return ResponseEntity.ok(reporteService.obtenerKPIs());
    }

    @GetMapping("/tipos-eventos-populares")
    public ResponseEntity<ReporteGraficoTortaDto> obtenerTiposEventosPopulares() {
        log.info("Solicitud de tipos de eventos populares recibida");
        return ResponseEntity.ok(reporteService.obtenerTiposEventosPopulares());
    }

    @GetMapping("/recaudacion-mensual")
    public ResponseEntity<ReporteGraficoMensualDto> obtenerRecaudacionMensual() {
        log.info("Solicitud de recaudación mensual recibida");
        return ResponseEntity.ok(reporteService.obtenerRecaudacionMensual());
    }

    @GetMapping("/inscripciones-mensuales")
    public ResponseEntity<ReporteGraficoInscripcionesMensualDto> obtenerInscripcionesMensuales() {
        log.info("Solicitud de inscripciones mensuales recibida");
        return ResponseEntity.ok(reporteService.obtenerInscripcionesMensuales());
    }

    @GetMapping("/historico-eventos")
    public ResponseEntity<Page<ReporteEventoDto>> obtenerHistoricoEventos(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) TipoEvento tipo,
            @RequestParam(required = false) Boolean esGratuito,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "horaInicio") String sort,
            @RequestParam(defaultValue = "DESC") String direction) {

        log.info("Solicitud de histórico de eventos - Página: {}, Tamaño: {}", page, size);

        Sort.Direction sortDirection = direction.equalsIgnoreCase("ASC") ?
                Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sort));

        return ResponseEntity.ok(reporteService.obtenerHistoricoEventos(nombre, tipo, esGratuito, pageable));
    }

    @GetMapping("/historico-eventos/exportar")
    public ResponseEntity<List<ReporteEventoDto>> obtenerHistoricoEventosParaExportar(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) TipoEvento tipo,
            @RequestParam(required = false) Boolean esGratuito) {

        Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE, Sort.by(Sort.Direction.DESC, "horaInicio"));
        Page<ReporteEventoDto> eventos = reporteService.obtenerHistoricoEventos(nombre, tipo, esGratuito, pageable);

        return ResponseEntity.ok(eventos.getContent());
    }
}