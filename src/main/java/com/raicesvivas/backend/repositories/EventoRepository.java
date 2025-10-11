package com.raicesvivas.backend.repositories;

import com.raicesvivas.backend.models.entities.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Integer> {
    List<Evento> findByOrganizadorId(int id);
}
