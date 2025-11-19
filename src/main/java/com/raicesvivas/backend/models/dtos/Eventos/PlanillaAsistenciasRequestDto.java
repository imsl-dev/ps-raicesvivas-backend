package com.raicesvivas.backend.models.dtos.Eventos;

import com.raicesvivas.backend.models.enums.UsuarioAsistencia;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlanillaAsistenciasRequestDto {
    private Integer eventoId;
    private List<UsuarioAsistencia> usuariosAsistencias;
}
