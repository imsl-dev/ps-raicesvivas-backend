package com.raicesvivas.backend.models.dtos.Eventos;

import com.raicesvivas.backend.models.enums.EstadoEvento;
import com.raicesvivas.backend.models.enums.TipoEvento;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventoRequestDto {

    private Integer id;

    @NotNull(message = "El tipo de evento es obligatorio")
    private TipoEvento tipo;

    @NotNull(message = "El estado del evento es obligatorio")
    private EstadoEvento estado;

    @NotNull(message = "El ID del organizador es obligatorio")
    @Positive(message = "El ID del organizador debe ser positivo")
    private Integer organizadorId;

    private Integer cuentaBancariaId;

    @NotNull(message = "El ID de la provincia es obligatorio")
    @Positive(message = "El ID de la provincia debe ser positivo")
    private Integer provinciaId;

    @NotBlank(message = "El nombre del evento es obligatorio")
    private String nombre;

    private String descripcion;

    private String rutaImg;

    private String direccion;
    private Double latitud;
    private Double longitud;

    @NotNull(message = "La hora de inicio es obligatoria")
    private LocalDateTime horaInicio;

    @NotNull(message = "La hora de fin es obligatoria")
    private LocalDateTime horaFin;

    private Integer puntosAsistencia = 0;

    private BigDecimal costoInterno;

    private BigDecimal costoInscripcion;

    private Integer sponsorId;
}