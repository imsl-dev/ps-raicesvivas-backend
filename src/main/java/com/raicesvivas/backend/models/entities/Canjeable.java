package com.raicesvivas.backend.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "canjeables")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Canjeable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="nombre",nullable = false)
    private String nombre;

    @Column(name = "sponsor_id", nullable = false)
    private Integer sponsorId;

    @Column(name = "url", length = 500)
    private String url;

    @Column(name = "costo_puntos", nullable = false)
    private Integer costoPuntos;

    @Column(name = "nombreSponsor",nullable = false)
    private String nombreSponsor;

    // 🔥 Reverse side of the Many-to-Many
    @ManyToMany(mappedBy = "canjeables")
    @JsonIgnore
    private List<Usuario> usuarios = new ArrayList<>();

    @Column(name="valido_hasta", nullable = false)
    private LocalDateTime validoHasta;
}