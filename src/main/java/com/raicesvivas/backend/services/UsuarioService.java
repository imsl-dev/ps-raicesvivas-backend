package com.raicesvivas.backend.services;

import com.raicesvivas.backend.models.dtos.ActualizarUsuarioDTO;
import com.raicesvivas.backend.models.dtos.NuevoUsuarioDTO;
import com.raicesvivas.backend.models.entities.Usuario;
import com.raicesvivas.backend.models.entities.auxiliar.Provincia;
import com.raicesvivas.backend.models.enums.RolUsuario;
import com.raicesvivas.backend.repositories.UsuarioRepository;
import com.raicesvivas.backend.repositories.auxiliar.ProvinciaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    private final ProvinciaRepository provinciaRepository;
    private final ModelMapper mapper;

    public List<Usuario> getAllUsuarios(){
        return usuarioRepository.findAll();
    }

    public Usuario guardarUsuario(NuevoUsuarioDTO dto) {
        Usuario nuevoUsuario = mapper.map(dto, Usuario.class);

        nuevoUsuario.setRol(RolUsuario.USUARIO);
        nuevoUsuario.setId(null);
        //find provincia by id
        Integer idProvincia = dto.getIdProvincia();
        Provincia provincia = provinciaRepository.findById(idProvincia).orElseThrow(()
                -> new EntityNotFoundException("Provincia con ID: "+idProvincia + " no encontrada"));

        nuevoUsuario.setProvincia(provincia);

        return usuarioRepository.save(nuevoUsuario);
    }

    public Usuario getUsuarioById(Integer id) {

        return usuarioRepository.findById(id).orElseThrow(()->
            new EntityNotFoundException("Usuario con ID "+id+" no encontrado")
        );
    }

    public Usuario actualizarUsuario(ActualizarUsuarioDTO dto) {

        Usuario usuarioExistente = getUsuarioById(dto.getId());

        Integer idProvincia = dto.getIdProvincia();
        Provincia provincia = provinciaRepository.findById(idProvincia).orElseThrow(()
                -> new EntityNotFoundException("Provincia con ID: "+idProvincia + " no encontrada"));

        usuarioExistente.setProvincia(provincia);

        usuarioExistente.setEmail(dto.getEmail());
        usuarioExistente.setNombre(dto.getNombre());
        usuarioExistente.setApellido(dto.getApellido());


        return usuarioRepository.save(usuarioExistente);
    }

    public boolean cambiarRol(Integer userId, RolUsuario rol) {
        Usuario userToModify = usuarioRepository.findById(userId).orElseThrow(()
                -> new EntityNotFoundException("Usuario no encontrado"));

        userToModify.setRol(rol);
        usuarioRepository.save(userToModify);
        return true;
    }
    }

