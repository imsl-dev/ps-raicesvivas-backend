package com.raicesvivas.backend.services;

import com.raicesvivas.backend.models.dtos.PeticionOrganizadorDTO;
import com.raicesvivas.backend.models.entities.PeticionOrganizador;
import com.raicesvivas.backend.repositories.PeticionOrganizadorRepository;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PeticionOrganizadorService {

    private final ModelMapper mapper;

    private final PeticionOrganizadorRepository peticionRepository;

    public boolean usuarioTienePeticion(Integer idUsuario) {
        PeticionOrganizador peticion = peticionRepository.findByUsuarioId(idUsuario);

        return peticion != null;
    }

    public PeticionOrganizador postPeticion(PeticionOrganizadorDTO dto) throws BadRequestException {

        if (usuarioTienePeticion(dto.getIdUsuario())) {
            throw new BadRequestException("Este usuario ya tiene una peticion activa");
        }


    }


}
