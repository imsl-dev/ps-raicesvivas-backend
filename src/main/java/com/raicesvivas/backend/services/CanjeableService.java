package com.raicesvivas.backend.services;

import com.raicesvivas.backend.models.dtos.NuevoCanjeableDTO;
import com.raicesvivas.backend.models.entities.Canjeable;
import com.raicesvivas.backend.repositories.CanjeableRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CanjeableService {

    private final CanjeableRepository canjeableRepository;
    private final ModelMapper mapper;

    public Canjeable postCanjeable(NuevoCanjeableDTO dto) {
        Canjeable nuevoCanjeable = new Canjeable();

        nuevoCanjeable.setId(null);
        nuevoCanjeable.setSponsorId(dto.getSponsorId());
        nuevoCanjeable.setUrl(dto.getUrl());
        nuevoCanjeable.setNombre(dto.getNombre());
        nuevoCanjeable.setCostoPuntos(dto.getCostoPuntos());
        nuevoCanjeable.setValidoHasta(dto.getValidoHasta());
        canjeableRepository.save(nuevoCanjeable);
        return nuevoCanjeable;
    }



}
