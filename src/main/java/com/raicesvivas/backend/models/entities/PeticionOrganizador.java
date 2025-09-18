package com.raicesvivas.backend.models.entities;

import com.raicesvivas.backend.models.enums.EstadoInscripcion;
import com.raicesvivas.backend.models.enums.EstadoPeticion;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity(name = "peticiones_organizadores")
@Data
public class PeticionOrganizador {

    @Id
    private int id;

    private Usuario usuario;

    private EstadoPeticion estadoPeticion;

    private String mensajeUsuario;
}
