package com.raicesvivas.backend.models.entities;

import com.raicesvivas.backend.models.entities.auxiliar.Provincia;
import com.raicesvivas.backend.models.enums.RolUsuario;
import com.raicesvivas.backend.models.enums.TipoDocumento;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity(name="usuarios")
@Data
public class Usuario {
    @Id
    private int id;

    private String nombre;

    private String apellido;

    private TipoDocumento tipoDocumento;

    private RolUsuario rol;

    private Provincia provincia;

    private int puntos;


}
