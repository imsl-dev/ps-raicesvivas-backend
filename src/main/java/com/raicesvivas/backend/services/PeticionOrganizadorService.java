package com.raicesvivas.backend.services;

import com.raicesvivas.backend.models.dtos.PeticionOrganizadorDTO;
import com.raicesvivas.backend.models.entities.PeticionOrganizador;
import com.raicesvivas.backend.models.entities.Usuario;
import com.raicesvivas.backend.models.enums.EstadoPeticion;
import com.raicesvivas.backend.models.enums.RolUsuario;
import com.raicesvivas.backend.repositories.PeticionOrganizadorRepository;
import com.raicesvivas.backend.repositories.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PeticionOrganizadorService {

    private final ModelMapper mapper;

    private final PeticionOrganizadorRepository peticionRepository;

    private final UsuarioRepository usuarioRepository;

    public boolean usuarioTienePeticion(Integer idUsuario) {
        PeticionOrganizador peticion = peticionRepository.findByUsuarioId(idUsuario);

        return peticion != null;
    }

    public PeticionOrganizador postPeticion(PeticionOrganizadorDTO dto) throws BadRequestException {


      if (usuarioTienePeticion(dto.getIdUsuario())) {
          throw new BadRequestException("Este usuario ya tiene una peticion activa");
      }

      PeticionOrganizador peticion = mapper.map(dto,PeticionOrganizador.class);
      peticion.setId(null);
      peticion.setEstadoPeticion(EstadoPeticion.PENDIENTE);

        //fetch name by id
        Usuario usuario = usuarioRepository.findById(dto.getIdUsuario()).orElseThrow(()
                -> new EntityNotFoundException("Usuario no encontrado"));

        peticion.setNombreUsuario(usuario.getNombre());
        peticion.setApellidoUsuario(usuario.getApellido());
        peticion.setEmail(usuario.getEmail());

      return peticionRepository.save(peticion);
    }

    public PeticionOrganizador getPeticionByUserId(Integer userId) {
        return peticionRepository.findByUsuarioId(userId);

    }

    public PeticionOrganizador getPeticionById(Integer id) {
        return peticionRepository.findById(id).orElseThrow( () -> new EntityNotFoundException("Peticion no encontrada"));
    }

    public PeticionOrganizador mutarEstadoPeticion(Integer peticionId, EstadoPeticion nuevoEstado){
        //obtener peticion a actualizar
        PeticionOrganizador peticion = getPeticionById(peticionId);

        peticion.setEstadoPeticion(nuevoEstado);
        peticionRepository.save(peticion);

        //actualizar rol del user si corresponde

        if (nuevoEstado == EstadoPeticion.ACEPTADO) {
            //encontrar user
            Usuario usuario = usuarioRepository.findById(peticion.getUsuarioId())
                    .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado para actualizar el rol"));
            usuario.setRol(RolUsuario.ORGANIZADOR);
            usuarioRepository.save(usuario);
        }
        return peticion;
    }

    public List<PeticionOrganizador> getAll() {
        return peticionRepository.findAll();
    }


}
