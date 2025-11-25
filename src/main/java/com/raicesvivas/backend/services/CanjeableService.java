package com.raicesvivas.backend.services;

import com.raicesvivas.backend.models.dtos.CanjeableDTO;
import com.raicesvivas.backend.models.dtos.NuevoCanjeableDTO;
import com.raicesvivas.backend.models.entities.Canjeable;
import com.raicesvivas.backend.models.entities.Sponsor;
import com.raicesvivas.backend.models.entities.Usuario;
import com.raicesvivas.backend.repositories.CanjeableRepository;
import com.raicesvivas.backend.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CanjeableService {

    private final CanjeableRepository canjeableRepository;
    private final UsuarioRepository usuarioRepository;
    private final ModelMapper mapper;
    private final SponsorService sponsorService;

    public Canjeable postCanjeable(NuevoCanjeableDTO dto) {
        Canjeable nuevoCanjeable = new Canjeable();

        nuevoCanjeable.setId(null);
        nuevoCanjeable.setSponsorId(dto.getSponsorId());
        nuevoCanjeable.setUrl(dto.getUrl());
        nuevoCanjeable.setNombre(dto.getNombre());
        nuevoCanjeable.setCostoPuntos(dto.getCostoPuntos());
        nuevoCanjeable.setValidoHasta(dto.getValidoHasta());
        nuevoCanjeable.setNombreSponsor(dto.getNombreSponsor());
        canjeableRepository.save(nuevoCanjeable);
        return nuevoCanjeable;
    }

    /**
     * Comprar un canjeable - Agrega el canjeable al usuario y descuenta los puntos
     */
    @Transactional
    public boolean comprarCanjeable(Integer usuarioId, Integer canjeableId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + usuarioId));

        Canjeable canjeable = canjeableRepository.findById(canjeableId)
                .orElseThrow(() -> new RuntimeException("Canjeable no encontrado con id: " + canjeableId));

        // Verificar que el usuario tenga suficientes puntos
        if (usuario.getPuntos() < canjeable.getCostoPuntos()) {
            throw new RuntimeException("Puntos insuficientes. Se requieren " + canjeable.getCostoPuntos() +
                    " puntos pero el usuario solo tiene " + usuario.getPuntos());
        }

        // Verificar que el canjeable no esté ya en la lista del usuario
        if (usuario.getCanjeables().contains(canjeable)) {
            throw new RuntimeException("El usuario ya posee este canjeable");
        }

        // Agregar el canjeable al usuario
        usuario.getCanjeables().add(canjeable);

        // Descontar los puntos
        usuario.setPuntos(usuario.getPuntos() - canjeable.getCostoPuntos());

        // Guardar el usuario (cascade guardará la relación)
        usuarioRepository.save(usuario);
        return true;
    }

    /**
     * Canjear cupón - Remueve el canjeable del usuario (marca como usado/canjeado)
     */
    @Transactional
    public Usuario canjearCupon(Integer usuarioId, Integer canjeableId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + usuarioId));

        Canjeable canjeable = canjeableRepository.findById(canjeableId)
                .orElseThrow(() -> new RuntimeException("Canjeable no encontrado con id: " + canjeableId));

        // Verificar que el usuario tenga este canjeable
        if (!usuario.getCanjeables().contains(canjeable)) {
            throw new RuntimeException("El usuario no posee este canjeable");
        }

        // Remover el canjeable del usuario (marcarlo como canjeado/usado)
        usuario.getCanjeables().remove(canjeable);

        // Guardar el usuario
        return usuarioRepository.save(usuario);
    }

    /**
     * Obtener todos los canjeables de un usuario
     */
    public List<CanjeableDTO> getCanjeablesByUsuarioId(Integer usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + usuarioId));

        // Get all sponsors
        List<Sponsor> sponsors = sponsorService.getAllSponsors();

        List<Canjeable> canjeables = usuario.getCanjeables();
        List<CanjeableDTO> canjeableDTOS = new ArrayList<>();

        for (Canjeable canjeable : canjeables) {
            CanjeableDTO dto = mapper.map(canjeable, CanjeableDTO.class);

            // Find sponsor by sponsorId
            Sponsor sponsor = sponsors.stream()
                    .filter(s -> s.getId().equals(canjeable.getSponsorId()))
                    .findFirst()
                    .orElse(null);

            dto.setSponsor(sponsor);
            canjeableDTOS.add(dto);
        }

        return canjeableDTOS;
    }


    /**
     * Obtener todos los canjeables disponibles (para mostrar en la tienda)
     * Solo retorna canjeables con fecha de vencimiento válida (validoHasta > now)
     */
    public List<CanjeableDTO> getAllCanjeablesDisponibles() {
        LocalDateTime now = LocalDateTime.now();
        List<Canjeable> canjeables = canjeableRepository.findAll().stream()
                .filter(canjeable -> canjeable.getValidoHasta().isAfter(now))
                .toList();

        List<Sponsor> sponsors = sponsorService.getAllSponsors();
        List<CanjeableDTO> response = new ArrayList<>();
        for (Canjeable canjeable : canjeables) {
            CanjeableDTO dto = mapper.map(canjeable,CanjeableDTO.class);
            // Find sponsor by sponsorId
            Sponsor sponsor = sponsors.stream()
                    .filter(s -> s.getId().equals(canjeable.getSponsorId()))
                    .findFirst()
                    .orElse(null);

            dto.setSponsor(sponsor);
            response.add(dto);
        }
        return response;
    }
}