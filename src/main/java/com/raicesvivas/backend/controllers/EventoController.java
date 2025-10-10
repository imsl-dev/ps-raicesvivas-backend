package com.raicesvivas.backend.controllers;

import com.raicesvivas.backend.models.dtos.EventoDto;
import com.raicesvivas.backend.models.entities.Evento;
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
    public ResponseEntity<List<Evento>> getAllEventos() {
        List<Evento> eventos = eventoService.getAllEventos();
        return ResponseEntity.ok(eventos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Evento> getEventoById(@PathVariable Integer id) {
        try {
            Evento evento = eventoService.getEventoById(id);
            return ResponseEntity.ok(evento);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Evento> createEvento(@Valid @RequestBody EventoDto eventoDto) {
        try {
            Evento newEvento = eventoService.saveEvento(eventoDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(newEvento);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Evento> updateEvento(@PathVariable Integer id, @Valid @RequestBody EventoDto eventoDto) {
        try {
            eventoDto.setId(id);
            Evento updatedEvento = eventoService.updateEvento(eventoDto);
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