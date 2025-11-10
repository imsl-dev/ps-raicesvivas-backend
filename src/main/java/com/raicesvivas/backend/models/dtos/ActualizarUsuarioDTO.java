package com.raicesvivas.backend.models.dtos;

import lombok.Data;

@Data
public class ActualizarUsuarioDTO {

    private Integer id;

    private String nombre;

    private String apellido;

    private Integer idProvincia;

    private String email;
}
