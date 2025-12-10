package com.raicesvivas.backend.models.dtos.reportes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReporteDonacionOrganizadorDto {
    private Integer donacionId;
    private String nombreEvento;
    private LocalDateTime fechaHora;
    private String nombreUsuario;
    private BigDecimal montoDonado;
    private String mensaje;
}