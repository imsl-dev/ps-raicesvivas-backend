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
    private final EmailService emailService;

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
        //TODO QUITAR PUNTOS HARDCODEADOS
        nuevoUsuario.setPuntos(1000);
        emailService.EnviarMailBienvienida(nuevoUsuario);
        return usuarioRepository.save(nuevoUsuario);
    }

    public Usuario getUsuarioById(Integer id) {

        return usuarioRepository.findById(id).orElseThrow(()->
                new EntityNotFoundException("Usuario con ID "+id+" no encontrado")
        );
    }

    public Usuario actualizarUsuario(ActualizarUsuarioDTO dto) {

        Usuario usuarioExistente = usuarioRepository.findById(dto.getId())
                .orElseThrow(() -> new EntityNotFoundException("Usuario con ID " + dto.getId() + " no encontrado"));

        // Actualizar campos básicos
        usuarioExistente.setNombre(dto.getNombre());
        usuarioExistente.setApellido(dto.getApellido());
        usuarioExistente.setEmail(dto.getEmail());

        // Actualizar provincia
        if (dto.getIdProvincia() != null) {
            Provincia provincia = provinciaRepository.findById(dto.getIdProvincia())
                    .orElseThrow(() -> new EntityNotFoundException("Provincia con ID " + dto.getIdProvincia() + " no encontrada"));
            usuarioExistente.setProvincia(provincia);
        }

        // Actualizar foto de perfil si se proporciona
        if (dto.getRutaImg() != null) {
            usuarioExistente.setRutaImg(dto.getRutaImg());
        }

        return usuarioRepository.save(usuarioExistente);
    }

    public Boolean cambiarRol(Integer idUsuario, RolUsuario nuevoRol) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new EntityNotFoundException("Usuario con ID " + idUsuario + " no encontrado"));

        usuario.setRol(nuevoRol);
        usuarioRepository.save(usuario);

        return true;
    }
}