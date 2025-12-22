package com.raicesvivas.backend.models.dtos.reportes;

import com.raicesvivas.backend.models.enums.EstadoEvento;
import com.raicesvivas.backend.models.enums.TipoEvento;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReporteEventoDto {
    private Integer id;
    private String nombre;
    private TipoEvento tipo;
    private LocalDateTime horaInicio;
    private String ubicacion;
    private EstadoEvento estado;
    private BigDecimal costoInscripcion;
    private Long totalInscripciones;
    private BigDecimal costoInterno;
    private String sponsorNombre;
}