package com.raicesvivas.backend.repositories;

import com.raicesvivas.backend.models.entities.Inscripcion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InscripcionRepository extends JpaRepository<Inscripcion, Integer> {
    Optional<Inscripcion> findByUsuarioIdAndEventoId (Integer usuarioId, Integer eventoId);
    List<Inscripcion> findByEventoId(Integer eventoId);
    @Query("SELECT COUNT(i) FROM Inscripcion i WHERE " +
            "YEAR(i.fechaCreacion) = :year AND MONTH(i.fechaCreacion) = :month")
    Long countByYearAndMonth(@Param("year") int year, @Param("month") int month);

    @Query("SELECT MONTH(i.fechaCreacion) as mes, COUNT(i) as total " +
            "FROM Inscripcion i WHERE YEAR(i.fechaCreacion) = :year " +
            "GROUP BY MONTH(i.fechaCreacion) " +
            "ORDER BY MONTH(i.fechaCreacion)")
    List<Object[]> findInscripcionesMensualesByYear(@Param("year") int year);

    Long countByEventoId(Integer eventoId);
}
