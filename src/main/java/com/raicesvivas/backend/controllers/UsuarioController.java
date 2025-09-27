package com.raicesvivas.backend.controllers;

import com.raicesvivas.backend.models.dtos.NuevoUsuarioDTO;
import com.raicesvivas.backend.models.entities.Usuario;
import com.raicesvivas.backend.services.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    private final ModelMapper mapper;

    public Usuario crearUsuario(NuevoUsuarioDTO dto) {
        Usuario nuevoUsuario = mapper.map(dto, Usuario.class);
        return nuevoUsuario;

    }
}
