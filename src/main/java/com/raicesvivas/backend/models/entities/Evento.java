package com.raicesvivas.backend.models.entities;

import com.raicesvivas.backend.models.entities.auxiliar.CuentaBancaria;
import com.raicesvivas.backend.models.entities.auxiliar.Provincia;
import com.raicesvivas.backend.models.enums.EstadoEvento;
import com.raicesvivas.backend.models.enums.TipoEvento;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;

@Entity(name = "eventos")
@Data
public class Evento {

    @Id
    private int id;

    private TipoEvento tipo;

    private EstadoEvento estado;

    private Usuario organizador;

    private CuentaBancaria cuentaBancaria;

    private Provincia provincia;

    private String direccion;

    private String nombre;

    private String descripcion;

    private String rutaImg;

    private Sponsor sponsor;

    private LocalDateTime horaInicio;

    private LocalDateTime horaFin;

    private int puntosAsistencia;

    private Double costoInterno;

    private Double costoInscripcion;


}
