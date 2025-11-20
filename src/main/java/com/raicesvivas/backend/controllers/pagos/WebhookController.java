package com.raicesvivas.backend.controllers.pagos;

import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.raicesvivas.backend.services.pagos.PagoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/webhooks")
@RequiredArgsConstructor
@Slf4j
public class WebhookController {

    private final PagoService pagoService;

    /**
     * Endpoint para recibir notificaciones de MercadoPago
     * POST /api/webhooks/mercadopago
     */
    @PostMapping("/mercadopago")
    public ResponseEntity<Void> recibirWebhook(@RequestBody Map<String, Object> payload) {

        log.info("Webhook recibido: {}", payload);

        try {
            // MercadoPago envía el tipo de notificación
            String type = (String) payload.get("type");

            if ("payment".equals(type)) {
                // Extraer el ID del pago
                Map<String, Object> data = (Map<String, Object>) payload.get("data");
                Long paymentId = Long.parseLong(data.get("id").toString());

                log.info("Procesando payment ID: {}", paymentId);

                // Procesar el pago
                pagoService.procesarWebhook(paymentId);

                return ResponseEntity.ok().build();
            }

            log.warn("Tipo de webhook no soportado: {}", type);
            return ResponseEntity.ok().build();

        } catch (MPException | MPApiException e) {
            log.error("Error procesando webhook de MercadoPago", e);
            return ResponseEntity.status(500).build();
        } catch (Exception e) {
            log.error("Error inesperado procesando webhook", e);
            return ResponseEntity.status(500).build();
        }
    }
}
