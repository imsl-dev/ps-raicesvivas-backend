package com.raicesvivas.backend.repositories.pagos;

import com.raicesvivas.backend.models.entities.Pago;
import com.raicesvivas.backend.models.enums.pagos.EstadoPago;
import com.raicesvivas.backend.models.enums.pagos.TipoPago;
import org.springframework.data.jpa.repository.JpaRepository;
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
}
