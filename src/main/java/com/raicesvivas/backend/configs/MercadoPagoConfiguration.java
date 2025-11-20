package com.raicesvivas.backend.configs;

import com.mercadopago.MercadoPagoConfig;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
@Slf4j
public class MercadoPagoConfiguration {  // Renombrado para evitar conflicto

    @Value("${mercadopago.access-token}")
    private String accessToken;

    @Value("${mercadopago.public-key}")
    private String publicKey;

    @Value("${mercadopago.webhook-secret:}")
    private String webhookSecret;

    @Value("${mercadopago.urls.success}")
    private String successUrl;

    @Value("${mercadopago.urls.failure}")
    private String failureUrl;

    @Value("${mercadopago.urls.pending}")
    private String pendingUrl;

    @Value("${mercadopago.notification-url}")  // ✅ Lee desde application.yml
    private String notificationUrl;

    @PostConstruct
    public void init() {
        log.info("=== Configurando MercadoPago ===");
        log.info("Access Token COMPLETO: '{}'", accessToken); // Temporal para debug
        log.info("Access Token longitud: {}", accessToken != null ? accessToken.length() : 0);
        log.info("Public Key: '{}'", publicKey);
        log.info("Success URL: {}", successUrl);
        log.info("Failure URL: {}", failureUrl);
        log.info("Pending URL: {}", pendingUrl);
        log.info("Notification URL: {}", notificationUrl);

        if (accessToken == null || accessToken.isEmpty()) {
            log.error("❌ ACCESS TOKEN NO CONFIGURADO - Verifica las variables de entorno");
            throw new IllegalStateException("MercadoPago Access Token no configurado");
        }

        if (!accessToken.startsWith("APP_USR-") && !accessToken.startsWith("APP-")) {
            log.warn("⚠️ El Access Token no parece ser válido (debe empezar con TEST- o APP-)");
            log.warn("⚠️ Token actual: {}", accessToken); // Ver qué tiene realmente
        }

        MercadoPagoConfig.setAccessToken(accessToken);
        log.info("✅ MercadoPago configurado");
    }
}
