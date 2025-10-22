package com.raicesvivas.backend.models.dtos.pagos;

import com.raicesvivas.backend.models.enums.pagos.EstadoPago;
import com.raicesvivas.backend.models.enums.pagos.TipoPago;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PagoResponseDto {
    private Integer id;
    private Integer usuarioId;
    private Integer eventoId;
    private TipoPago tipoPago;
    private EstadoPago estadoPago;
    private BigDecimal monto;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;
    private String mercadoPagoPreferenceId;
    private String mercadoPagoPaymentId;
    private String mercadoPagoStatus;

    // Información adicional para el frontend
    private String nombreEvento;
    private String nombreUsuario;
}
