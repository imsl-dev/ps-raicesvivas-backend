package com.raicesvivas.backend.models.dtos;

import com.raicesvivas.backend.models.entities.Sponsor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CanjeableDTO {
    private Integer id;
    private String nombre;
    private Integer sponsorId;
    private String url;
    private Integer costoPuntos;
    private LocalDateTime validoHasta;
    private Sponsor sponsor;
}
