package com.raicesvivas.backend.models.entities;

import com.raicesvivas.backend.models.enums.EstadoInscripcion;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity(name = "inscripciones")
@Data
public class Inscripcion {

    @Id
    private int id;

    private EstadoInscripcion estado;

    private Usuario usuario;

    private Evento evento;
}
