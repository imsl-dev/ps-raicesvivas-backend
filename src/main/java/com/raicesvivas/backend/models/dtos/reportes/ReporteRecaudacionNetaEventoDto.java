package com.raicesvivas.backend.models.dtos.reportes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReporteRecaudacionNetaEventoDto {
    private List<String> nombresEventos;
    private List<BigDecimal> recaudacionNeta; // donaciones - costo interno
}