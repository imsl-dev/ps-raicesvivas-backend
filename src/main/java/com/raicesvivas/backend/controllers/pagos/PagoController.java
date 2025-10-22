package com.raicesvivas.backend.controllers.pagos;

import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.raicesvivas.backend.models.dtos.pagos.MercadoPagoResponseDto;
import com.raicesvivas.backend.models.dtos.pagos.PagoRequestDto;
import com.raicesvivas.backend.models.dtos.pagos.PagoResponseDto;
import com.raicesvivas.backend.services.pagos.PagoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pagos")
@RequiredArgsConstructor
@Slf4j
public class PagoController {

    private final PagoService pagoService;

    /**
     * Crea un nuevo pago y retorna la URL de MercadoPago
     * POST /api/pagos
     */
    @PostMapping
    public ResponseEntity<MercadoPagoResponseDto> crearPago(@Valid @RequestBody PagoRequestDto request) {
        try {
            MercadoPagoResponseDto response = pagoService.crearPago(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (MPException | MPApiException e) {
            log.error("Error al crear preferencia de MercadoPago", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (IllegalStateException e) {
            log.warn("Validación fallida: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    /**
     * Obtiene un pago por ID
     * GET /api/pagos/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<PagoResponseDto> obtenerPago(@PathVariable Integer id) {
        PagoResponseDto pago = pagoService.obtenerPagoPorId(id);
        return ResponseEntity.ok(pago);
    }

    /**
     * Obtiene todos los pagos de un usuario
     * GET /api/pagos/usuario/{usuarioId}
     */
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<PagoResponseDto>> obtenerPagosPorUsuario(@PathVariable Integer usuarioId) {
        List<PagoResponseDto> pagos = pagoService.obtenerPagosPorUsuario(usuarioId);
        return ResponseEntity.ok(pagos);
    }

    /**
     * Obtiene todos los pagos de un evento
     * GET /api/pagos/evento/{eventoId}
     */
    @GetMapping("/evento/{eventoId}")
    public ResponseEntity<List<PagoResponseDto>> obtenerPagosPorEvento(@PathVariable Integer eventoId) {
        List<PagoResponseDto> pagos = pagoService.obtenerPagosPorEvento(eventoId);
        return ResponseEntity.ok(pagos);
    }

    /**
     * Verifica si un usuario ya pagó la inscripción a un evento
     * GET /api/pagos/verificar?usuarioId={usuarioId}&eventoId={eventoId}
     */
    @GetMapping("/verificar")
    public ResponseEntity<Boolean> verificarPago(
            @RequestParam Integer usuarioId,
            @RequestParam Integer eventoId) {
        boolean pagado = pagoService.verificarPagoInscripcion(usuarioId, eventoId);
        return ResponseEntity.ok(pagado);
    }
}
