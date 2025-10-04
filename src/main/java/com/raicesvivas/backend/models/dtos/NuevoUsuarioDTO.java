package com.raicesvivas.backend.models.dtos;

import com.raicesvivas.backend.models.enums.RolUsuario;
import com.raicesvivas.backend.models.enums.TipoDocumento;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class NuevoUsuarioDTO {

    @NotBlank
    private String nombre;

    @NotBlank
    private String apellido;

    @NotNull
    private TipoDocumento tipoDocumento;

    @NotBlank
    private String nroDocumento;

    @NotNull(message = "Provincia is required")
    @Positive(message = "El id provincia debe ser un numero positivo")
    private Integer idProvincia;

}
