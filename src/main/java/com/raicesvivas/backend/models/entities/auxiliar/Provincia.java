package com.raicesvivas.backend.models.entities.auxiliar;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "provincias")
@Data
public class Provincia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nombre;
}
