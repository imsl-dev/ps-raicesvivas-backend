package com.raicesvivas.backend.controllers;

import com.raicesvivas.backend.models.dtos.NuevoUsuarioDTO;
import com.raicesvivas.backend.models.entities.Usuario;
import com.raicesvivas.backend.services.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@RestController
//@RequestMapping("/api/users")
//@RequiredArgsConstructor
//public class UsuarioController {
//
//    private final UsuarioService usuarioService;
//
//    private final ModelMapper mapper;
//    @PostMapping()
//    public Usuario crearUsuario(@Valid @RequestBody NuevoUsuarioDTO dto) {
//        Usuario nuevoUsuario = mapper.map(dto, Usuario.class);
//        return nuevoUsuario;
//
//    }
//}
