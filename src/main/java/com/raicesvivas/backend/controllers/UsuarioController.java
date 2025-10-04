package com.raicesvivas.backend.controllers;

import com.raicesvivas.backend.models.dtos.NuevoUsuarioDTO;
import com.raicesvivas.backend.models.entities.Usuario;
import com.raicesvivas.backend.models.entities.auxiliar.Provincia;
import com.raicesvivas.backend.models.enums.RolUsuario;
import com.raicesvivas.backend.repositories.auxiliar.ProvinciaRepository;
import com.raicesvivas.backend.services.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    private final ProvinciaRepository provinciaRepo;

    private final ModelMapper mapper;
    @PostMapping()
    public ResponseEntity<Usuario> crearUsuario(@Valid @RequestBody NuevoUsuarioDTO dto) {

        return ResponseEntity.ok(usuarioService.guardarUsuario(dto));
    }

    @GetMapping()
    public List<Usuario> getUsuarios() {
        return usuarioService.getAllUsuarios();
    }
}
