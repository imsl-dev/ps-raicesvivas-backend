package com.raicesvivas.backend.services;

import com.raicesvivas.backend.models.dtos.NuevoUsuarioDTO;
import com.raicesvivas.backend.models.entities.Usuario;
import com.raicesvivas.backend.models.entities.auxiliar.Provincia;
import com.raicesvivas.backend.repositories.UsuarioRepository;
import com.raicesvivas.backend.repositories.auxiliar.ProvinciaRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepo;

    private final ProvinciaRepository provinciaRepo;

    private final ModelMapper mapper;

    public Usuario guardarUsuario(NuevoUsuarioDTO dto) {

        Usuario nuevoUsuario = mapper.map(dto, Usuario.class);

        //encontrar provincia por id de dto

        Provincia provincia = provinciaRepo.findById(dto.idProvincia).orElseThrow();

        nuevoUsuario.setProvincia(provincia);

        return usuarioRepo.save(nuevoUsuario);

    }
}
