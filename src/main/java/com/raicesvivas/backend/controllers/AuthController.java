package com.raicesvivas.backend.controllers;

import com.raicesvivas.backend.models.dtos.UsuarioLoginDTO;
import com.raicesvivas.backend.models.entities.PeticionOrganizador;
import com.raicesvivas.backend.models.entities.Usuario;
import com.raicesvivas.backend.models.enums.RolUsuario;
import com.raicesvivas.backend.repositories.PeticionOrganizadorRepository;
import com.raicesvivas.backend.repositories.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.hibernate.Hibernate.map;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UsuarioRepository usuarioRepository;

    private final ModelMapper mapper;

    private final PeticionOrganizadorRepository peticionOrganizadorRepository;

    /**Intenta realizar un login con email y password
     * Si los campos no coinciden -> EntityNotFound
     * @param email email del usuario
     * @param password password del usuario
     * @return rol del usuario
     * */
    @GetMapping("/login")
    public UsuarioLoginDTO intentarLogin(@RequestParam String email, @RequestParam String password) {

        Usuario findUsuario = usuarioRepository
                .findUserByEmailAndPassword(email,password)
                .orElseThrow(() ->new EntityNotFoundException("El Email o la contraseña son incorrectos"));

        //buscar si tiene peticion activa

        PeticionOrganizador peticion = peticionOrganizadorRepository.findByUsuarioId(findUsuario.getId());

        UsuarioLoginDTO result =mapper.map(findUsuario, UsuarioLoginDTO.class);

        if (peticion != null) {
            result.setEstadoPeticionOrganizador(peticion.getEstadoPeticion());
        }
        else result.setEstadoPeticionOrganizador(null);

        return result;

    }

    @GetMapping("/email/disponible")
    public boolean verificarEmailDisponible(@RequestParam String email) {
        Usuario usuario = usuarioRepository.findByEmail(email);
        System.out.println("Usuario: "+usuario);
        return usuario == null;
    }
}
