package com.raicesvivas.backend.models.dtos.reportes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReporteTasaAsistenciaDto {
    private List<String> labels;  // ["Presentes", "Ausentes"]
    private List<Long> values;    // [cantidadPresentes, cantidadAusentes]
    private Long totalInscripciones;
    private Double porcentajeAsistencia;
}