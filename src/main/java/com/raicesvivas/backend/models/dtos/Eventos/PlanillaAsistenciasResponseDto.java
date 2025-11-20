package com.raicesvivas.backend.models.dtos.Eventos;

import com.raicesvivas.backend.models.enums.UsuarioAsistencia;
import com.raicesvivas.backend.models.enums.UsuarioNombreAsistencia;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlanillaAsistenciasResponseDto {
    private Integer eventoId;
    private List<UsuarioNombreAsistencia> usuariosAsistencias;
}
