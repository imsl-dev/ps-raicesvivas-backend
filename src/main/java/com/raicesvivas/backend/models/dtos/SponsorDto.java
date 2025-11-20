package com.raicesvivas.backend.models.dtos;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SponsorDto {
    private Integer id;

    private String nombre;

    private String linkDominio;

    private String rutaImg1;

    private String rutaImg2;
    private Boolean activo;
}
