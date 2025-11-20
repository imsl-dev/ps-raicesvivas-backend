package com.raicesvivas.backend.models.entities;

import com.raicesvivas.backend.models.enums.pagos.EstadoPago;
import com.raicesvivas.backend.models.enums.pagos.TipoPago;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pagos")
public class Pago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "usuario_id", nullable = false)
    private Integer usuarioId;

    @Column(name = "evento_id")  // Nullable para donaciones generales
    private Integer eventoId;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_pago", nullable = false)
    private TipoPago tipoPago;  // INSCRIPCION o DONACION

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_pago", nullable = false)
    private EstadoPago estadoPago;

    @Column(name = "monto", nullable = false, precision = 10, scale = 2)
    private BigDecimal monto;

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;

    // SOLO UTILIZADO EN DONACIONES
    @Column(name = "mensaje", columnDefinition = "TEXT")
    private String mensaje;

    // Datos de MercadoPago
    @Column(name = "mercadopago_preference_id")
    private String mercadoPagoPreferenceId;  // ID de la preferencia creada

    @Column(name = "mercadopago_payment_id")
    private String mercadoPagoPaymentId;      // ID del pago confirmado

    @Column(name = "mercadopago_status")
    private String mercadoPagoStatus;         // approved, rejected, etc

    @Column(name = "detalles", columnDefinition = "TEXT")
    private String detalles;  // JSON con info adicional de MP
}