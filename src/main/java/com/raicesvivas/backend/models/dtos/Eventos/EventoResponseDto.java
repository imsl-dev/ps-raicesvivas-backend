package com.raicesvivas.backend.models.dtos;

import com.raicesvivas.backend.models.enums.EstadoEvento;
import com.raicesvivas.backend.models.enums.TipoEvento;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventoResponseDto {

    private Integer id;
    private TipoEvento tipo;
    private EstadoEvento estado;

    // IDs de las relaciones
    private Integer organizadorId;
    private Integer cuentaBancariaId;
    private Integer provinciaId;
    private Integer sponsorId;

    // Nombres para mostrar (opcional pero útil)
    private String organizadorNombre;
    private String organizadorApellido;
    private String organizadorEmail;
    private String organizadorRutaImg;
    private String provinciaNombre;
    private String sponsorNombre;

    // Datos del evento
    private String nombre;
    private String descripcion;
    private String rutaImg;
    private String direccion;
    private Double latitud;
    private Double longitud;
    private LocalDateTime horaInicio;
    private LocalDateTime horaFin;
    private Integer puntosAsistencia;
    private BigDecimal costoInterno;
    private BigDecimal costoInscripcion;
}