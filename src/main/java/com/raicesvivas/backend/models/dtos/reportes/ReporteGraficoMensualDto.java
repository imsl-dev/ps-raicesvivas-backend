package com.raicesvivas.backend.models.dtos.reportes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReporteGraficoMensualDto {
    private List<String> meses;
    private List<BigDecimal> valores;
}