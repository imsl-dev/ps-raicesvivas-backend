package com.raicesvivas.backend.models.entities.auxiliar;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity(name="provincias")
@Data
public class Provincia {

    @Id
    private int id;

    private String nombre;
}
