package com.raicesvivas.backend.models.dtos.reportes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReporteKPIDto {
    private BigDecimal totalRecaudadoMes;
    private Long inscripcionesMes;
    private Long eventosActivos;
}