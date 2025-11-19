package com.raicesvivas.backend.repositories;

import com.raicesvivas.backend.models.entities.Inscripcion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InscripcionRepository extends JpaRepository<Inscripcion, Integer> {
    Optional<Inscripcion> findByUsuarioIdAndEventoId (Integer usuarioId, Integer eventoId);
    List<Inscripcion> findByEventoId(Integer eventoId);
}
