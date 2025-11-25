package com.raicesvivas.backend.models.dtos;

import com.raicesvivas.backend.models.entities.Canjeable;
import com.raicesvivas.backend.models.enums.EstadoPeticion;
import lombok.Data;

import java.util.List;

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

    private String rutaImg;

    private List<CanjeableDTO> canjeables;

}
