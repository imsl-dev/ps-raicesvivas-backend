package com.raicesvivas.backend.controllers.reportes;

import com.raicesvivas.backend.models.dtos.reportes.*;
import com.raicesvivas.backend.models.enums.TipoEvento;
import com.raicesvivas.backend.services.reportes.ReporteOrganizadorService;
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
@RequestMapping("/api/reportes/organizador")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "http://localhost:4200")
public class ReporteOrganizadorController {

    private final ReporteOrganizadorService reporteOrganizadorService;

    @GetMapping("/{organizadorId}/kpis")
    public ResponseEntity<ReporteOrganizadorKPIDto> obtenerKPIs(
            @PathVariable Integer organizadorId) {
        log.info("Solicitud de KPIs para organizador: {}", organizadorId);
        return ResponseEntity.ok(reporteOrganizadorService.obtenerKPIsOrganizador(organizadorId));
    }

    @GetMapping("/{organizadorId}/tasa-asistencia")
    public ResponseEntity<ReporteTasaAsistenciaDto> obtenerTasaAsistencia(
            @PathVariable Integer organizadorId) {
        log.info("Solicitud de tasa de asistencia para organizador: {}", organizadorId);
        return ResponseEntity.ok(reporteOrganizadorService.obtenerTasaAsistencia(organizadorId));
    }

    @GetMapping("/{organizadorId}/recaudacion-neta")
    public ResponseEntity<ReporteRecaudacionNetaEventoDto> obtenerRecaudacionNeta(
            @PathVariable Integer organizadorId) {
        log.info("Solicitud de recaudación neta para organizador: {}", organizadorId);
        return ResponseEntity.ok(reporteOrganizadorService.obtenerRecaudacionNetaPorEvento(organizadorId));
    }

    @GetMapping("/{organizadorId}/donaciones")
    public ResponseEntity<List<ReporteDonacionOrganizadorDto>> obtenerDonaciones(
            @PathVariable Integer organizadorId) {
        log.info("Solicitud de donaciones para organizador: {}", organizadorId);
        return ResponseEntity.ok(reporteOrganizadorService.obtenerDonacionesOrganizador(organizadorId));
    }

    @GetMapping("/{organizadorId}/historico-eventos")
    public ResponseEntity<Page<ReporteEventoDto>> obtenerHistoricoEventos(
            @PathVariable Integer organizadorId,
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) TipoEvento tipo,
            @RequestParam(required = false) Boolean esGratuito,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "horaInicio") String sort,
            @RequestParam(defaultValue = "DESC") String direction) {

        log.info("Solicitud de histórico de eventos para organizador {} - Página: {}, Tamaño: {}",
                organizadorId, page, size);

        Sort.Direction sortDirection = direction.equalsIgnoreCase("ASC") ?
                Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sort));

        return ResponseEntity.ok(reporteOrganizadorService.obtenerHistoricoEventosOrganizador(
                organizadorId, nombre, tipo, esGratuito, pageable));
    }

    @GetMapping("/{organizadorId}/historico-eventos/exportar")
    public ResponseEntity<List<ReporteEventoDto>> obtenerHistoricoEventosParaExportar(
            @PathVariable Integer organizadorId,
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) TipoEvento tipo,
            @RequestParam(required = false) Boolean esGratuito) {

        log.info("Solicitud de exportación de eventos para organizador: {}", organizadorId);

        return ResponseEntity.ok(reporteOrganizadorService.obtenerEventosOrganizadorParaExportar(
                organizadorId, nombre, tipo, esGratuito));
    }
}