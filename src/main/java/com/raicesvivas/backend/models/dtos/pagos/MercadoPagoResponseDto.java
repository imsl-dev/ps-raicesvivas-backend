package com.raicesvivas.backend.models.dtos.pagos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MercadoPagoResponseDto {
    private String preferenceId;
    private String initPoint; // URL de pago para redirección
    private String sandboxInitPoint; // URL de pago en sandbox
    private Integer pagoId; // ID interno del pago
}
