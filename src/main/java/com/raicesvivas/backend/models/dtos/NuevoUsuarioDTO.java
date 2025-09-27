package com.raicesvivas.backend.models.dtos;

import com.raicesvivas.backend.models.enums.RolUsuario;
import com.raicesvivas.backend.models.enums.TipoDocumento;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class NuevoUsuarioDTO {

    @NotBlank
    public String nombre;

    @NotBlank
    public String apellido;

    @NotNull
    public TipoDocumento tipoDocumento;

    @NotBlank
    public String nroDocumento;

    @NotNull
    public RolUsuario rol;

    @NotBlank
    public Integer idProvincia;

}
