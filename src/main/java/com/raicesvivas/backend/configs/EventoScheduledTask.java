package com.raicesvivas.backend.configs;

import com.raicesvivas.backend.models.entities.Evento;
import com.raicesvivas.backend.models.enums.EstadoEvento;
import com.raicesvivas.backend.repositories.EventoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class EventoScheduledTask {

    private final EventoRepository eventoRepository;

    /**
     * Tarea programada que se ejecuta cada 5 minutos
     * Actualiza el estado de los eventos según su fecha/hora
     */
    @Scheduled(fixedRateString = "${scheduler.eventos.actualizar-estados-rate}")
    @Transactional
    public void actualizarEstadosEventos() {
        LocalDateTime ahora = LocalDateTime.now();

        // Buscar eventos que deben cambiar a EN_CURSO
        List<Evento> eventosProximos = eventoRepository.findByEstadoAndHoraInicioLessThanEqual(
                EstadoEvento.PROXIMO,
                ahora
        );

        for (Evento evento : eventosProximos) {
            // Solo cambiar a EN_CURSO si aún no ha finalizado
            if (evento.getHoraFin().isAfter(ahora)) {
                evento.setEstado(EstadoEvento.EN_CURSO);
                eventoRepository.save(evento);
                log.info("Evento '{}' (ID: {}) cambió a EN_CURSO", evento.getNombre(), evento.getId());
            } else {
                // Si ya pasó la hora de fin, marcar como FINALIZADO directamente
                evento.setEstado(EstadoEvento.FINALIZADO);
                eventoRepository.save(evento);
                log.info("Evento '{}' (ID: {}) cambió a FINALIZADO", evento.getNombre(), evento.getId());
            }
        }

        // Buscar eventos EN_CURSO que deben cambiar a FINALIZADO
        List<Evento> eventosEnCurso = eventoRepository.findByEstadoAndHoraFinLessThanEqual(
                EstadoEvento.EN_CURSO,
                ahora
        );

        for (Evento evento : eventosEnCurso) {
            evento.setEstado(EstadoEvento.FINALIZADO);
            eventoRepository.save(evento);
            log.info("Evento '{}' (ID: {}) cambió a FINALIZADO", evento.getNombre(), evento.getId());
        }

        if (!eventosProximos.isEmpty() || !eventosEnCurso.isEmpty()) {
            log.info("Actualización de estados completada. Próximos: {}, En Curso: {}",
                    eventosProximos.size(), eventosEnCurso.size());
        }
    }
}