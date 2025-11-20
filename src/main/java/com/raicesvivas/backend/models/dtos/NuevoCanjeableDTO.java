package com.raicesvivas.backend.models.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NuevoCanjeableDTO {

    private String nombre;

    private Integer costoPuntos;

    private Integer sponsorId;

    private String url;

    private LocalDateTime validoHasta;
}
