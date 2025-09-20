package com.raicesvivas.backend.models.entities;

import com.raicesvivas.backend.models.entities.auxiliar.Provincia;
import com.raicesvivas.backend.models.enums.RolUsuario;
import com.raicesvivas.backend.models.enums.TipoDocumento;
import jakarta.persistence.*;
import lombok.Data;

@Entity()
@Table(name="usuarios")
@Data
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nombre;

    private String apellido;

    @Enumerated(EnumType.STRING)
    private TipoDocumento tipoDocumento;

    @Enumerated(EnumType.STRING)
    private RolUsuario rol;

    @ManyToOne
    @JoinColumn(name = "provincia_id", referencedColumnName = "id")
    private Provincia provincia;

    private int puntos;


}
