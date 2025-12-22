package com.raicesvivas.backend.models.dtos.reportes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReporteGraficoInscripcionesMensualDto {
    private List<String> meses;
    private List<Long> valores;
}