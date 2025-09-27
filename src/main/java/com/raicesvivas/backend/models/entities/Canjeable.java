package com.raicesvivas.backend.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "canjeables")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Canjeable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "sponsor_id", nullable = false)
    private Integer sponsorId;

    @Column(name = "link_pdf_drive", length = 500)
    private String linkPdfDrive;

    @Column(name = "costo_puntos", nullable = false)
    private Integer costoPuntos;
}