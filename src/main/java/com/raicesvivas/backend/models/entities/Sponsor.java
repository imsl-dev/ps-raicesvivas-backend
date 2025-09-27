package com.raicesvivas.backend.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "sponsors")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sponsor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nombre", nullable = false, length = 255)
    private String nombre;

    @Column(name = "ruta_img1", length = 500)
    private String rutaImg1;

    @Column(name = "ruta_img2", length = 500)
    private String rutaImg2;

    // Ya no necesita lista de eventos - relación manejada desde Evento
}