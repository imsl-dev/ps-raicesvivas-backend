package com.raicesvivas.backend.repositories;

import com.raicesvivas.backend.models.entities.Evento;
import com.raicesvivas.backend.models.enums.EstadoEvento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Integer> {
    List<Evento> findByOrganizadorId(int id);
    List<Evento> findByEstadoAndHoraInicioLessThanEqual(EstadoEvento estado, LocalDateTime fecha);
    List<Evento> findByEstadoAndHoraFinLessThanEqual(EstadoEvento estado, LocalDateTime fecha);
}
