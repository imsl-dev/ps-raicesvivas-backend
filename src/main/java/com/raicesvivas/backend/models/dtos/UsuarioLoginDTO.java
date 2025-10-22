package com.raicesvivas.backend.models.dtos;

import com.raicesvivas.backend.models.enums.EstadoPeticion;
import lombok.Data;

@Data
public class UsuarioLoginDTO {

    private Integer id;

    private String email;

    private String nombre;

    private String apellido;

    private String tipoDocumento;

    private String nroDocumento;

    private String rol;

    private String provincia;

    private Integer puntos;

    private EstadoPeticion estadoPeticionOrganizador;

}
