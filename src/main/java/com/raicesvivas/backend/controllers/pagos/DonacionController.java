package com.raicesvivas.backend.controllers.pagos;

import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.raicesvivas.backend.models.dtos.pagos.DonacionRequestDto;
import com.raicesvivas.backend.models.dtos.pagos.MercadoPagoResponseDto;
import com.raicesvivas.backend.models.dtos.pagos.PagoResponseDto;
import com.raicesvivas.backend.services.pagos.DonacionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/donaciones")
@RequiredArgsConstructor
@Slf4j
public class DonacionController {

    private final DonacionService donacionService;

    /**
     * Crea una nueva donación y retorna la URL de pago de MercadoPago
     * POST /api/donaciones
     */
    @PostMapping
    public ResponseEntity<MercadoPagoResponseDto> realizarDonacion(@Valid @RequestBody DonacionRequestDto request) {
        try {
            MercadoPagoResponseDto response = donacionService.crearDonacion(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (MPException | MPApiException e) {
            log.error("Error al crear preferencia de MercadoPago para donación", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (IllegalArgumentException e) {
            log.warn("Validación fallida: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * Obtiene las últimas 10 donaciones aprobadas
     * GET /api/donaciones/ultimas
     */
    @GetMapping("/ultimas")
    public ResponseEntity<List<PagoResponseDto>> obtenerUltimasDonaciones() {
        List<PagoResponseDto> donaciones = donacionService.obtenerUltimasDonaciones();
        return ResponseEntity.ok(donaciones);
    }
}