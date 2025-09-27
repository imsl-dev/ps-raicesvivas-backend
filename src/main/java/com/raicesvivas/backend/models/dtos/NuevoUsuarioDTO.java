package com.raicesvivas.backend.models.dtos;

import jakarta.validation.constraints.NotBlank;

public class NuevoUsuarioDTO {

    @NotBlank
    private String nombre;

    @NotBlank
    private String apellido;
}
