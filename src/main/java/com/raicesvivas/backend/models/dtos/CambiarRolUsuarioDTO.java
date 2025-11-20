package com.raicesvivas.backend.models.dtos;

import com.raicesvivas.backend.models.enums.RolUsuario;
import lombok.Data;

@Data
public class CambiarRolUsuarioDTO {
    private Integer idUsuario;

    private RolUsuario nuevoRol;
}
