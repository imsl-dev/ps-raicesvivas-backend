package com.raicesvivas.backend.services;

import com.raicesvivas.backend.models.entities.Usuario;
import com.raicesvivas.backend.models.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository userRepo;

    public Usuario guardarUsuario() {


    }
}
