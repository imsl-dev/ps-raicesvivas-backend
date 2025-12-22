package com.raicesvivas.backend.repositories.pagos;

import com.raicesvivas.backend.models.entities.Pago;
import com.raicesvivas.backend.models.enums.pagos.EstadoPago;
import com.raicesvivas.backend.models.enums.pagos.TipoPago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Integer> {

    List<Pago> findByUsuarioId(Integer usuarioId);

    List<Pago> findByEventoId(Integer eventoId);

    List<Pago> findByUsuarioIdAndEventoId(Integer usuarioId, Integer eventoId);

    Optional<Pago> findByMercadoPagoPreferenceId(String preferenceId);

    Optional<Pago> findByMercadoPagoPaymentId(String paymentId);

    List<Pago> findByEstadoPago(EstadoPago estadoPago);

    List<Pago> findByTipoPago(TipoPago tipoPago);

    boolean existsByUsuarioIdAndEventoIdAndEstadoPago(
            Integer usuarioId,
            Integer eventoId,
            EstadoPago estadoPago
    );

    List<Pago> findByTipoPagoAndEstadoPagoOrderByFechaCreacionDesc(
            TipoPago tipoPago,
            EstadoPago estadoPago,
            Pageable pageable
    );

    List<Pago> findByTipoPagoAndEstadoPagoAndMensajeIsNotNullOrderByFechaCreacionDesc(
            TipoPago tipoPago,
            EstadoPago estadoPago,
            Pageable pageable
    );

    boolean existsByUsuarioIdAndEventoIdAndTipoPagoAndEstadoPago(
            Integer usuarioId,
            Integer eventoId,
            TipoPago tipoPago,
            EstadoPago estadoPago
    );


    @Query("SELECT p FROM Pago p WHERE p.tipoPago = 'DONACION' " +
            "AND p.estadoPago = 'APROBADO' " +
            "AND YEAR(p.fechaCreacion) = :year " +
            "AND MONTH(p.fechaCreacion) = :month")
    List<Pago> findDonacionesByYearAndMonth(@Param("year") int year, @Param("month") int month);


    @Query("SELECT MONTH(p.fechaCreacion) as mes, " +
            "CAST(SUM(p.monto * 0.05) AS java.math.BigDecimal) as total " +
            "FROM Pago p WHERE p.tipoPago = 'DONACION' " +
            "AND p.estadoPago = 'APROBADO' " +
            "AND YEAR(p.fechaCreacion) = :year " +
            "GROUP BY MONTH(p.fechaCreacion) " +
            "ORDER BY MONTH(p.fechaCreacion)")
    List<Object[]> findRecaudacionMensualByYear(@Param("year") int year);
}
