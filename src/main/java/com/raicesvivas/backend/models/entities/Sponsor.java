package com.raicesvivas.backend.models.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity(name = "sponsors")
@Data
public class Sponsor {

    @Id
    private int id;

    private String nombre;

    private String rutaImg1;

    private String rutaImg2;

}
