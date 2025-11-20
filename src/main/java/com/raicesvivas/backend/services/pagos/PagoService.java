package com.raicesvivas.backend.services.pagos;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.payment.Payment;
import com.raicesvivas.backend.models.dtos.pagos.MercadoPagoResponseDto;
import com.raicesvivas.backend.models.dtos.pagos.PagoRequestDto;
import com.raicesvivas.backend.models.dtos.pagos.PagoResponseDto;
import com.raicesvivas.backend.models.entities.Evento;
import com.raicesvivas.backend.models.entities.Pago;
import com.raicesvivas.backend.models.entities.Usuario;
import com.raicesvivas.backend.models.enums.pagos.EstadoPago;
import com.raicesvivas.backend.models.enums.pagos.TipoPago;
import com.raicesvivas.backend.repositories.EventoRepository;
import com.raicesvivas.backend.repositories.UsuarioRepository;
import com.raicesvivas.backend.repositories.pagos.PagoRepository;
import com.raicesvivas.backend.services.EventoService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PagoService {

    private final PagoRepository pagoRepository;
    private final UsuarioRepository usuarioRepository;
    private final EventoRepository eventoRepository;
    private final MercadoPagoService mercadoPagoService;
    private final EventoService eventoService;

    /**
     * Crea un nuevo pago y genera la preferencia de MercadoPago
     */
    @Transactional
    public MercadoPagoResponseDto crearPago(PagoRequestDto request) throws MPException, MPApiException {

        // Validar usuario
        Usuario usuario = usuarioRepository.findById(request.getUsuarioId())
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        // Validar evento si es inscripción
        if (request.getEventoId() != null) {
            Evento evento = eventoRepository.findById(request.getEventoId())
                    .orElseThrow(() -> new EntityNotFoundException("Evento no encontrado"));

            // Verificar si ya existe un pago de INSCRIPCION aprobado para este evento
            // (Las donaciones NO deben bloquear la inscripción)
            if (request.getTipoPago() == TipoPago.INSCRIPCION) {
                boolean yaExistePagoInscripcion = pagoRepository.existsByUsuarioIdAndEventoIdAndTipoPagoAndEstadoPago(
                        request.getUsuarioId(),
                        request.getEventoId(),
                        TipoPago.INSCRIPCION,
                        EstadoPago.APROBADO
                );

                if (yaExistePagoInscripcion) {
                    throw new IllegalStateException("Ya existe un pago de inscripción aprobado para este evento");
                }
            }
        }

        // Crear registro de pago
        Pago pago = new Pago();
        pago.setUsuarioId(request.getUsuarioId());
        pago.setEventoId(request.getEventoId());
        pago.setTipoPago(request.getTipoPago());
        pago.setEstadoPago(EstadoPago.PENDIENTE);
        pago.setMonto(request.getMonto());
        pago.setFechaCreacion(LocalDateTime.now());

        Pago pagoGuardado = pagoRepository.save(pago);
        log.info("Pago creado con ID: {}", pagoGuardado.getId());

        // Crear preferencia en MercadoPago
        MercadoPagoResponseDto mpResponse = mercadoPagoService.crearPreferencia(request, pagoGuardado.getId());

        // Actualizar el pago con el preferenceId
        pagoGuardado.setMercadoPagoPreferenceId(mpResponse.getPreferenceId());
        pagoRepository.save(pagoGuardado);

        log.info("Preferencia de MercadoPago creada: {}", mpResponse.getPreferenceId());

        return mpResponse;
    }

    /**
     * Procesa la notificación de webhook de MercadoPago
     */
    @Transactional
    public void procesarWebhook(Long paymentId) throws MPException, MPApiException {

        // Consultar el pago en MercadoPago
        Payment payment = mercadoPagoService.consultarPago(paymentId);

        log.info("Procesando webhook para payment ID: {} con estado: {}", paymentId, payment.getStatus());

        // Buscar el pago en nuestra BD por externalReference
        String externalReference = payment.getExternalReference();
        if (externalReference == null) {
            log.warn("Payment sin external reference: {}", paymentId);
            return;
        }

        Integer pagoId = Integer.parseInt(externalReference);
        Pago pago = pagoRepository.findById(pagoId)
                .orElseThrow(() -> new EntityNotFoundException("Pago no encontrado: " + pagoId));

        // Actualizar estado según el status de MercadoPago
        actualizarEstadoPago(pago, payment);

        // SOLO inscribir automáticamente si el pago es de tipo INSCRIPCION y fue aprobado
        if (pago.getEstadoPago() == EstadoPago.APROBADO &&
                pago.getTipoPago() == TipoPago.INSCRIPCION &&
                pago.getEventoId() != null) {

            log.info("Pago de inscripción aprobado - Creando inscripción automática");

            try {
                // Verificar que no esté ya inscripto
                boolean yaInscripto = eventoService.validarInscripcionEvento(pago.getUsuarioId(), pago.getEventoId());

                if (!yaInscripto) {
                    eventoService.inscribirseEvento(pago.getUsuarioId(), pago.getEventoId());
                    log.info("Inscripción automática creada para usuario {} en evento {}",
                            pago.getUsuarioId(), pago.getEventoId());
                } else {
                    log.info("El usuario ya está inscripto - No se crea inscripción duplicada");
                }
            } catch (Exception e) {
                log.error("Error al crear inscripción automática tras pago aprobado", e);
                // No lanzamos la excepción para no afectar la actualización del pago
            }
        }

        pagoRepository.save(pago);
        log.info("Pago {} actualizado a estado: {}", pago.getId(), pago.getEstadoPago());
    }

    /**
     * Obtiene un pago por ID
     */
    public PagoResponseDto obtenerPagoPorId(Integer pagoId) {
        Pago pago = pagoRepository.findById(pagoId)
                .orElseThrow(() -> new EntityNotFoundException("Pago no encontrado"));
        return convertirADto(pago);
    }

    /**
     * Obtiene todos los pagos de un usuario
     */
    public List<PagoResponseDto> obtenerPagosPorUsuario(Integer usuarioId) {
        return pagoRepository.findByUsuarioId(usuarioId).stream()
                .map(this::convertirADto)
                .collect(Collectors.toList());
    }

    /**
     * Obtiene todos los pagos de un evento
     */
    public List<PagoResponseDto> obtenerPagosPorEvento(Integer eventoId) {
        return pagoRepository.findByEventoId(eventoId).stream()
                .map(this::convertirADto)
                .collect(Collectors.toList());
    }

    /**
     * Verifica si un usuario ya pagó la inscripción a un evento
     */
    public boolean verificarPagoInscripcion(Integer usuarioId, Integer eventoId) {
        return pagoRepository.existsByUsuarioIdAndEventoIdAndTipoPagoAndEstadoPago(
                usuarioId, eventoId, TipoPago.INSCRIPCION, EstadoPago.APROBADO
        );
    }

    // ========== MÉTODOS PRIVADOS ==========

    /**
     * Actualiza el estado del pago según la respuesta de MercadoPago
     */
    private void actualizarEstadoPago(Pago pago, Payment payment) {
        pago.setMercadoPagoPaymentId(payment.getId().toString());
        pago.setMercadoPagoStatus(payment.getStatus());
        pago.setFechaActualizacion(LocalDateTime.now());

        // Mapear el estado de MercadoPago a nuestro enum
        switch (payment.getStatus()) {
            case "approved":
                pago.setEstadoPago(EstadoPago.APROBADO);
                break;
            case "rejected":
            case "cancelled":
                pago.setEstadoPago(EstadoPago.RECHAZADO);
                break;
            case "pending":
            case "in_process":
            case "in_mediation":
                pago.setEstadoPago(EstadoPago.PENDIENTE);
                break;
            default:
                log.warn("Estado desconocido de MercadoPago: {}", payment.getStatus());
        }

        // Guardar detalles adicionales
        pago.setDetalles(String.format(
                "{\"status_detail\":\"%s\",\"payment_method_id\":\"%s\",\"payment_type_id\":\"%s\"}",
                payment.getStatusDetail(),
                payment.getPaymentMethodId(),
                payment.getPaymentTypeId()
        ));
    }

    /**
     * Convierte una entidad Pago a DTO
     */
    private PagoResponseDto convertirADto(Pago pago) {
        PagoResponseDto dto = new PagoResponseDto();
        dto.setId(pago.getId());
        dto.setUsuarioId(pago.getUsuarioId());
        dto.setEventoId(pago.getEventoId());
        dto.setTipoPago(pago.getTipoPago());
        dto.setEstadoPago(pago.getEstadoPago());
        dto.setMonto(pago.getMonto());
        dto.setFechaCreacion(pago.getFechaCreacion());
        dto.setFechaActualizacion(pago.getFechaActualizacion());
        dto.setMercadoPagoPreferenceId(pago.getMercadoPagoPreferenceId());
        dto.setMercadoPagoPaymentId(pago.getMercadoPagoPaymentId());
        dto.setMercadoPagoStatus(pago.getMercadoPagoStatus());

        // Cargar nombres si están disponibles
        if (pago.getEventoId() != null) {
            eventoRepository.findById(pago.getEventoId())
                    .ifPresent(evento -> dto.setNombreEvento(evento.getNombre()));
        }

        usuarioRepository.findById(pago.getUsuarioId())
                .ifPresent(usuario -> dto.setNombreUsuario(usuario.getNombre() + " " + usuario.getApellido()));

        return dto;
    }
}
