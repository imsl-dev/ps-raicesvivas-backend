package com.raicesvivas.backend.models.entities;

import com.raicesvivas.backend.models.enums.EstadoInscripcion;
import com.raicesvivas.backend.models.enums.EstadoPeticion;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "peticiones_organizadores")
@Data
public class PeticionOrganizador {

    @Id
    private int id;

    private Usuario usuario;

    @Enumerated(EnumType.STRING)
    private EstadoPeticion estadoPeticion;

    private String mensajeUsuario;
}
