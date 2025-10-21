package com.raicesvivas.backend.models.enums.pagos;

public enum EstadoPago {
    PENDIENTE,      // Pago iniciado pero no completado
    APROBADO,       // Pago confirmado por MercadoPago
    RECHAZADO,      // Pago rechazado por falta de fondos, etc
    CANCELADO       // Usuario canceló antes de pagar
}
