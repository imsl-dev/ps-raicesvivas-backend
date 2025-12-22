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
        nuevoCanjeable.setActivo(true); // ⭐ NUEVO: Por defecto activo
        canjeableRepository.save(nuevoCanjeable);
        return nuevoCanjeable;
    }

    /**
     * ⭐ NUEVO: Obtener TODOS los canjeables (para administración)
     * Incluye activos, inactivos, vigentes y vencidos
     */
    public List<CanjeableDTO> getAllCanjeablesAdmin() {
        List<Canjeable> canjeables = canjeableRepository.findAll();
        List<Sponsor> sponsors = sponsorService.getAllSponsors();
        List<CanjeableDTO> response = new ArrayList<>();

        for (Canjeable canjeable : canjeables) {
            CanjeableDTO dto = mapper.map(canjeable, CanjeableDTO.class);
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

    /**
     * ⭐ NUEVO: Obtener un canjeable por ID
     */
    public CanjeableDTO getCanjeableById(Integer id) {
        Canjeable canjeable = canjeableRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Canjeable no encontrado con id: " + id));

        CanjeableDTO dto = mapper.map(canjeable, CanjeableDTO.class);

        // Obtener sponsor
        List<Sponsor> sponsors = sponsorService.getAllSponsors();
        Sponsor sponsor = sponsors.stream()
                .filter(s -> s.getId().equals(canjeable.getSponsorId()))
                .findFirst()
                .orElse(null);

        dto.setSponsor(sponsor);
        return dto;
    }

    /**
     * ⭐ NUEVO: Actualizar un canjeable
     */
    @Transactional
    public Canjeable updateCanjeable(Integer id, NuevoCanjeableDTO dto) {
        Canjeable canjeable = canjeableRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Canjeable no encontrado con id: " + id));

        // Verificar que esté vigente antes de permitir edición
        if (canjeable.getValidoHasta().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("No se puede editar un canjeable vencido");
        }

        canjeable.setNombre(dto.getNombre());
        canjeable.setSponsorId(dto.getSponsorId());
        canjeable.setUrl(dto.getUrl());
        canjeable.setCostoPuntos(dto.getCostoPuntos());
        canjeable.setValidoHasta(dto.getValidoHasta());
        canjeable.setNombreSponsor(dto.getNombreSponsor());
        canjeable.setActivo(dto.getActivo() != null ? dto.getActivo() : true);

        return canjeableRepository.save(canjeable);
    }

    /**
     * ⭐ NUEVO: Soft delete - Marca activo = false
     */
    @Transactional
    public Boolean deleteCanjeable(Integer id) {
        Canjeable canjeable = canjeableRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Canjeable no encontrado con id: " + id));

        // Verificar que esté vigente antes de permitir eliminación
        if (canjeable.getValidoHasta().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("No se puede eliminar un canjeable vencido");
        }

        canjeable.setActivo(false);
        canjeableRepository.save(canjeable);
        return true;
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
            throw new RuntimeException("Puntos insuficientes. Necesitas " + canjeable.getCostoPuntos() +
                    " puntos, tienes " + usuario.getPuntos());
        }

        // Verificar que el canjeable esté activo
        if (!canjeable.getActivo()) {
            throw new RuntimeException("Este canjeable no está disponible");
        }

        // Verificar que el canjeable no esté vencido
        if (canjeable.getValidoHasta().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Este canjeable ha vencido");
        }

        // Descontar puntos
        usuario.setPuntos(usuario.getPuntos() - canjeable.getCostoPuntos());

        // Agregar canjeable al usuario
        usuario.getCanjeables().add(canjeable);

        usuarioRepository.save(usuario);
        return true;
    }

    /**
     * Canjear un cupón - Elimina la relación del usuario con el canjeable
     */
    @Transactional
    public Usuario canjearCupon(Integer usuarioId, Integer canjeableId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + usuarioId));

        Canjeable canjeable = canjeableRepository.findById(canjeableId)
                .orElseThrow(() -> new RuntimeException("Canjeable no encontrado con id: " + canjeableId));

        // Remover el canjeable del usuario
        usuario.getCanjeables().remove(canjeable);

        return usuarioRepository.save(usuario);
    }

    /**
     * Obtener canjeables de un usuario
     */
    public List<CanjeableDTO> getCanjeablesByUsuarioId(Integer usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + usuarioId));

        List<Canjeable> canjeables = usuario.getCanjeables();
        List<Sponsor> sponsors = sponsorService.getAllSponsors();
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
     * Solo retorna canjeables activos y vigentes
     */
    public List<CanjeableDTO> getAllCanjeablesDisponibles() {
        LocalDateTime now = LocalDateTime.now();
        List<Canjeable> canjeables = canjeableRepository.findAll().stream()
                .filter(canjeable -> canjeable.getActivo() && canjeable.getValidoHasta().isAfter(now))
                .toList();

        List<Sponsor> sponsors = sponsorService.getAllSponsors();
        List<CanjeableDTO> response = new ArrayList<>();

        for (Canjeable canjeable : canjeables) {
            CanjeableDTO dto = mapper.map(canjeable, CanjeableDTO.class);
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