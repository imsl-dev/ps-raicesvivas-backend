package com.raicesvivas.backend.models.dtos.pagos;

import com.raicesvivas.backend.models.enums.pagos.TipoPago;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PagoRequestDto {

    @NotNull(message = "El ID del usuario es obligatorio")
    private Integer usuarioId;

    @NotNull(message = "El ID del evento es obligatorio")
    private Integer eventoId;

    @NotNull(message = "El tipo de pago es obligatorio")
    private TipoPago tipoPago;

    @NotNull(message = "El monto es obligatorio")
    @Positive(message = "El monto debe ser positivo")
    private BigDecimal monto;

    private String descripcion;
}
