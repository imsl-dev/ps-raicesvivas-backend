package com.raicesvivas.backend.repositories;

import com.raicesvivas.backend.models.entities.Evento;
import com.raicesvivas.backend.models.entities.Inscripcion;
import com.raicesvivas.backend.models.enums.EstadoEvento;
import com.raicesvivas.backend.models.enums.TipoEvento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Integer> {
    List<Evento> findByOrganizadorId(int id);
    List<Evento> findByEstadoAndHoraInicioLessThanEqual(EstadoEvento estado, LocalDateTime fecha);
    List<Evento> findByEstadoAndHoraFinLessThanEqual(EstadoEvento estado, LocalDateTime fecha);


    @Query("SELECT COUNT(e) FROM Evento e WHERE e.estado IN ('PROXIMO', 'EN_CURSO')")
    Long countEventosActivos();

    @Query("SELECT e.tipo, COUNT(e) FROM Evento e GROUP BY e.tipo ORDER BY COUNT(e) DESC")
    List<Object[]> findTiposEventosMasPopulares();


    @Query("SELECT e FROM Evento e WHERE " +
            "(:nombre IS NULL OR :nombre = '' OR LOWER(CAST(e.nombre AS string)) LIKE LOWER(CONCAT('%', :nombre, '%'))) AND " +
            "(:tipo IS NULL OR e.tipo = :tipo) AND " +
            "(:esGratuito IS NULL OR " +
            "   ((:esGratuito = true AND (e.costoInscripcion IS NULL OR e.costoInscripcion = 0)) OR " +
            "    (:esGratuito = false AND e.costoInscripcion IS NOT NULL AND e.costoInscripcion > 0)))")
    Page<Evento> findEventosConFiltros(
            @Param("nombre") String nombre,
            @Param("tipo") TipoEvento tipo,
            @Param("esGratuito") Boolean esGratuito,
            Pageable pageable
    );
}
