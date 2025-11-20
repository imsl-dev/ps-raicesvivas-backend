package com.raicesvivas.backend.models.dtos;

import com.raicesvivas.backend.models.enums.EstadoPeticion;
import lombok.Data;

@Data
public class MutarEstadoPeticionDTO {

    private Integer peticionId;

    private EstadoPeticion nuevoEstado;
}
