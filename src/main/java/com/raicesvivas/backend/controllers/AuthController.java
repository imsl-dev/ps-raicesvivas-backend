package com.raicesvivas.backend.controllers;

import com.raicesvivas.backend.models.entities.Usuario;
import com.raicesvivas.backend.models.enums.RolUsuario;
import com.raicesvivas.backend.repositories.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UsuarioRepository usuarioRepository;

    /**Intenta realizar un login con email y password
     * Si los campos no coinciden -> EntityNotFound
     * @param email email del usuario
     * @param password password del usuario
     * @return rol del usuario
     * */
    @GetMapping()
    public RolUsuario intentarLogin(@RequestParam String email, @RequestParam String password) {

        Usuario findUsuario = usuarioRepository
                .findUserByEmailAndPassword(email,password)
                .orElseThrow(() ->new EntityNotFoundException("El Email o la contraseña son incorrectos"));

        return findUsuario.getRol();

    }
}
