package com.raicesvivas.backend.services.pagos;

import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.preference.PreferenceBackUrlsRequest;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.payment.Payment;
import com.mercadopago.resources.preference.Preference;
import com.raicesvivas.backend.configs.MercadoPagoConfiguration;
import com.raicesvivas.backend.models.dtos.pagos.MercadoPagoResponseDto;
import com.raicesvivas.backend.models.dtos.pagos.PagoRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.Collections;

@Service
@RequiredArgsConstructor
@Slf4j
public class MercadoPagoService {

    private final MercadoPagoConfiguration mercadoPagoConfiguration;

    /**
     * Crea una preferencia de pago en MercadoPago
     * @param request Datos del pago
     * @param pagoId ID interno del pago
     * @return Respuesta con preferenceId y URL de pago
     */
    public MercadoPagoResponseDto crearPreferencia(PagoRequestDto request, Integer pagoId) throws MPException, MPApiException {

        // Construir el item del pago
        PreferenceItemRequest itemRequest = PreferenceItemRequest.builder()
                .id(pagoId.toString())
                .title(request.getDescripcion() != null ? request.getDescripcion() : "Pago - Raíces Vivas")
                .description(generarDescripcion(request))
                .quantity(1)
                .currencyId("ARS")
                .unitPrice(request.getMonto())
                .build();

        log.info("Construyendo BackUrls:");
        log.info("  - Success base: {}", mercadoPagoConfiguration.getSuccessUrl());
        log.info("  - Failure base: {}", mercadoPagoConfiguration.getFailureUrl());
        log.info("  - Pending base: {}", mercadoPagoConfiguration.getPendingUrl());

        String successUrl = mercadoPagoConfiguration.getSuccessUrl() + "?pagoId=" + pagoId;
        String failureUrl = mercadoPagoConfiguration.getFailureUrl() + "?pagoId=" + pagoId;
        String pendingUrl = mercadoPagoConfiguration.getPendingUrl() + "?pagoId=" + pagoId;

        log.info("URLs completas:");
        log.info("  - Success: {}", successUrl);
        log.info("  - Failure: {}", failureUrl);
        log.info("  - Pending: {}", pendingUrl);

        // Configurar URLs de retorno
        PreferenceBackUrlsRequest backUrls = PreferenceBackUrlsRequest.builder()
                .success(mercadoPagoConfiguration.getSuccessUrl() + "?pagoId=" + pagoId)
                .failure(mercadoPagoConfiguration.getFailureUrl() + "?pagoId=" + pagoId)
                .pending(mercadoPagoConfiguration.getPendingUrl() + "?pagoId=" + pagoId)
                .build();

        log.info("BackUrls configuradas:");
        log.info("  - Success: {}", mercadoPagoConfiguration.getSuccessUrl() + "?pagoId=" + pagoId);
        log.info("  - Failure: {}", mercadoPagoConfiguration.getFailureUrl() + "?pagoId=" + pagoId);
        log.info("  - Pending: {}", mercadoPagoConfiguration.getPendingUrl() + "?pagoId=" + pagoId);

        // Construir la preferencia
        PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                .items(Collections.singletonList(itemRequest))
                .backUrls(backUrls)
                .autoReturn("approved") // Retorno automático cuando se aprueba
                .externalReference(pagoId.toString())
                .statementDescriptor("RAICES VIVAS")
                .notificationUrl(mercadoPagoConfiguration.getNotificationUrl())
                .build();

        // Crear la preferencia en MercadoPago
        PreferenceClient client = new PreferenceClient();

        try {
            Preference preference = client.create(preferenceRequest);
            log.info("Preferencia creada exitosamente: {}", preference.getId());

            return MercadoPagoResponseDto.builder()
                    .preferenceId(preference.getId())
                    .initPoint(preference.getInitPoint())
                    .sandboxInitPoint(preference.getSandboxInitPoint())
                    .pagoId(pagoId)
                    .build();

        } catch (MPApiException e) {
            log.error("Error de API de MercadoPago:");
            log.error("Status Code: {}", e.getStatusCode());
            log.error("Message: {}", e.getMessage());

            // Mostrar detalles completos del error
            if (e.getApiResponse() != null) {
                log.error("API Response Content: {}", e.getApiResponse().getContent());
                log.error("API Response Status Code: {}", e.getApiResponse().getStatusCode());
            }

            // Mostrar la preferencia que intentamos crear
            log.error("Preferencia enviada:");
            log.error("  - Items: {}", itemRequest);
            log.error("  - Back URLs: {}", backUrls);
            log.error("  - External Reference: {}", pagoId);
            log.error("  - Notification URL: {}", mercadoPagoConfiguration.getNotificationUrl());

            throw e;
        }
    }

    /**
     * Consulta el estado de un pago en MercadoPago
     * @param paymentId ID del pago en MercadoPago
     * @return Objeto Payment con la información del pago
     */
    public Payment consultarPago(Long paymentId) throws MPException, MPApiException {
        PaymentClient client = new PaymentClient();
        return client.get(paymentId);
    }

    /**
     * Genera una descripción legible para el pago
     */
    private String generarDescripcion(PagoRequestDto request) {
        StringBuilder descripcion = new StringBuilder();

        switch (request.getTipoPago()) {
            case INSCRIPCION:
                descripcion.append("Inscripción a evento");
                if (request.getEventoId() != null) {
                    descripcion.append(" #").append(request.getEventoId());
                }
                break;
            case DONACION:
                descripcion.append("Donación a Raíces Vivas");
                break;
        }

        return descripcion.toString();
    }
}