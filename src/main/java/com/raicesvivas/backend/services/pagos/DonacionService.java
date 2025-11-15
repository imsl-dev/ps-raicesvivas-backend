package com.raicesvivas.backend.services.pagos;

import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.raicesvivas.backend.models.dtos.pagos.DonacionRequestDto;
import com.raicesvivas.backend.models.dtos.pagos.MercadoPagoResponseDto;
import com.raicesvivas.backend.models.dtos.pagos.PagoRequestDto;
import com.raicesvivas.backend.models.dtos.pagos.PagoResponseDto;
import com.raicesvivas.backend.models.entities.Pago;
import com.raicesvivas.backend.models.entities.Usuario;
import com.raicesvivas.backend.models.enums.pagos.EstadoPago;
import com.raicesvivas.backend.models.enums.pagos.TipoPago;
import com.raicesvivas.backend.repositories.UsuarioRepository;
import com.raicesvivas.backend.repositories.pagos.PagoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class DonacionService {

    private final PagoRepository pagoRepository;
    private final UsuarioRepository usuarioRepository;
    private final MercadoPagoService mercadoPagoService;

    /**
     * Crea una nueva donación y genera la preferencia de MercadoPago
     * @param request Datos de la donación
     * @return Respuesta con preferenceId y URL de pago
     */
    @Transactional
    public MercadoPagoResponseDto crearDonacion(DonacionRequestDto request) throws MPException, MPApiException {

        // Validar usuario
        Usuario usuario = usuarioRepository.findById(request.getUsuarioId())
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        // Validar monto mínimo
        if (request.getMonto().compareTo(BigDecimal.valueOf(100)) < 0) {
            throw new IllegalArgumentException("El monto mínimo de donación es $100");
        }

        // Crear registro de pago como donación
        Pago pago = new Pago();
        pago.setUsuarioId(request.getUsuarioId());
        pago.setEventoId(null); // Las donaciones no tienen evento asociado
        pago.setTipoPago(TipoPago.DONACION);
        pago.setEstadoPago(EstadoPago.PENDIENTE);
        pago.setMonto(request.getMonto());
        pago.setFechaCreacion(LocalDateTime.now());

        Pago pagoGuardado = pagoRepository.save(pago);
        log.info("Donación creada con ID: {}", pagoGuardado.getId());

        // Convertir a PagoRequestDto para reutilizar la lógica de MercadoPago
        PagoRequestDto pagoRequest = new PagoRequestDto();
        pagoRequest.setUsuarioId(request.getUsuarioId());
        pagoRequest.setEventoId(null);
        pagoRequest.setTipoPago(TipoPago.DONACION);
        pagoRequest.setMonto(request.getMonto());
        pagoRequest.setDescripcion("Donación a Raíces Vivas - " + usuario.getNombre() + " " + usuario.getApellido());

        // Crear preferencia en MercadoPago
        MercadoPagoResponseDto mpResponse = mercadoPagoService.crearPreferencia(pagoRequest, pagoGuardado.getId());

        // Actualizar el pago con el preferenceId
        pagoGuardado.setMercadoPagoPreferenceId(mpResponse.getPreferenceId());
        pagoRepository.save(pagoGuardado);

        log.info("Preferencia de MercadoPago creada para donación: {}", mpResponse.getPreferenceId());

        return mpResponse;
    }

    /**
     * Obtiene las últimas 10 donaciones aprobadas ordenadas por fecha
     * @return Lista de las últimas 10 donaciones
     */
    public List<PagoResponseDto> obtenerUltimasDonaciones() {
        // Crear Pageable para limitar a 10 resultados ordenados por fecha de creación descendente
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "fechaCreacion"));

        // Buscar donaciones aprobadas
        List<Pago> donaciones = pagoRepository.findByTipoPagoAndEstadoPagoOrderByFechaCreacionDesc(
                TipoPago.DONACION,
                EstadoPago.APROBADO,
                pageable
        );

        log.info("Obteniendo últimas {} donaciones", donaciones.size());

        return donaciones.stream()
                .map(this::convertirADto)
                .collect(Collectors.toList());
    }

    /**
     * Convierte una entidad Pago a PagoResponseDto
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

        // Cargar nombre del usuario
        usuarioRepository.findById(pago.getUsuarioId())
                .ifPresent(usuario -> dto.setNombreUsuario(usuario.getNombre() + " " + usuario.getApellido()));

        return dto;
    }
}