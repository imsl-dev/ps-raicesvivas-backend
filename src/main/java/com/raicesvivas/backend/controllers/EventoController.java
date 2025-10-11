package com.raicesvivas.backend.controllers;

import com.raicesvivas.backend.models.dtos.EventoResponseDto;
import com.raicesvivas.backend.models.dtos.Eventos.EventoRequestDto;
import com.raicesvivas.backend.services.EventoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/eventos")
@RequiredArgsConstructor
public class EventoController {

    private final EventoService eventoService;

    @GetMapping
    public ResponseEntity<List<EventoResponseDto>> getAllEventos() {
        List<EventoResponseDto> eventos = eventoService.getAllEventos();
        return ResponseEntity.ok(eventos);
    }

    @GetMapping("/organizador/{id}")
    public ResponseEntity<List<EventoResponseDto>> getAllEventosOrganizador(@PathVariable Integer id) {
        try{
            List<EventoResponseDto> eventos = eventoService.getAllEventosOrganizador(id);
            return ResponseEntity.ok(eventos);
        } catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventoResponseDto> getEventoById(@PathVariable Integer id) {
        try {
            EventoResponseDto evento = eventoService.getEventoById(id);
            return ResponseEntity.ok(evento);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<EventoResponseDto> createEvento(@Valid @RequestBody EventoRequestDto eventoDto) {
        try {
            EventoResponseDto newEvento = eventoService.saveEvento(eventoDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(newEvento);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventoResponseDto> updateEvento(@PathVariable Integer id, @Valid @RequestBody EventoRequestDto eventoDto) {
        try {
            eventoDto.setId(id);
            EventoResponseDto updatedEvento = eventoService.updateEvento(eventoDto);
            return ResponseEntity.ok(updatedEvento);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvento(@PathVariable Integer id) {
        try {
            eventoService.deleteEventoById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}