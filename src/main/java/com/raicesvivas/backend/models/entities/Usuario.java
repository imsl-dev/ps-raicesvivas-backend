package com.raicesvivas.backend.models.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Usuario {
    @Id
    private int id;

    private String nombre;

    private String apellido;

    
}
