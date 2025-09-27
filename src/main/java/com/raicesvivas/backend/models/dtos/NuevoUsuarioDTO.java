package com.raicesvivas.backend.models.dtos;

import com.raicesvivas.backend.models.enums.RolUsuario;
import com.raicesvivas.backend.models.enums.TipoDocumento;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class NuevoUsuarioDTO {

    @NotBlank
    private String nombre;

    @NotBlank
    private String apellido;

    @NotNull
    private TipoDocumento tipoDocumento;

    @NotBlank
    private String nroDocumento;

    @NotNull
    private RolUsuario rol;

    @NotBlank
    private Integer idProvincia;

}
