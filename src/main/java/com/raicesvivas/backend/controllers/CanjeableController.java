package com.raicesvivas.backend.controllers;


import com.raicesvivas.backend.models.dtos.CanjeableDTO;
import com.raicesvivas.backend.models.dtos.NuevoCanjeableDTO;
import com.raicesvivas.backend.models.entities.Canjeable;
import com.raicesvivas.backend.models.entities.Usuario;
import com.raicesvivas.backend.services.CanjeableService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/canjeables")
@RequiredArgsConstructor
public class CanjeableController {

    private final CanjeableService canjeableService;

    /**
     * Crear un nuevo canjeable (Admin)
     */
    @PostMapping()
    public ResponseEntity<Canjeable> postCanjeable(@RequestBody NuevoCanjeableDTO dto) {
        System.out.println("Controller: " + dto.toString());
        return ResponseEntity.ok(canjeableService.postCanjeable(dto));
    }

    /**
     * Obtener todos los canjeables disponibles (para la tienda)
     */
    @GetMapping()
    public ResponseEntity<List<CanjeableDTO>> getAllCanjeables() {
        return ResponseEntity.ok(canjeableService.getAllCanjeablesDisponibles());
    }

    /**
     * Obtener canjeables de un usuario específico
     * GET /api/canjeables/usuario/{usuarioId}
     */
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<CanjeableDTO>> getCanjeablesByUsuario(@PathVariable Integer usuarioId) {
        return ResponseEntity.ok(canjeableService.getCanjeablesByUsuarioId(usuarioId));
    }

    /**
     * Comprar un canjeable (agregar al usuario y descontar puntos)
     * POST /api/canjeables/comprar/{usuarioId}/{canjeableId}
     */
    @PostMapping("/comprar/{usuarioId}/{canjeableId}")
    public ResponseEntity<Boolean> comprarCanjeable(
            @PathVariable Integer usuarioId,
            @PathVariable Integer canjeableId) {
        return ResponseEntity.ok(canjeableService.comprarCanjeable(usuarioId, canjeableId));
    }

    /**
     * Canjear un cupón (remover del usuario - marcar como usado)
     * DELETE /api/canjeables/canjear/{usuarioId}/{canjeableId}
     */
    @DeleteMapping("/canjear/{usuarioId}/{canjeableId}")
    public ResponseEntity<Usuario> canjearCupon(
            @PathVariable Integer usuarioId,
            @PathVariable Integer canjeableId) {
        return ResponseEntity.ok(canjeableService.canjearCupon(usuarioId, canjeableId));
    }

    /**
     * ⭐ NUEVO: Obtener TODOS los canjeables (activos e inactivos, vigentes y vencidos)
     * Para administración
     */
    @GetMapping("/all")
    public ResponseEntity<List<CanjeableDTO>> getAllCanjeablesAdmin() {
        return ResponseEntity.ok(canjeableService.getAllCanjeablesAdmin());
    }

    /**
     * ⭐ NUEVO: Obtener un canjeable por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<CanjeableDTO> getCanjeableById(@PathVariable Integer id) {
        return ResponseEntity.ok(canjeableService.getCanjeableById(id));
    }

    /**
     * ⭐ NUEVO: Actualizar un canjeable
     */
    @PutMapping("/{id}")
    public ResponseEntity<Canjeable> updateCanjeable(
            @PathVariable Integer id,
            @RequestBody NuevoCanjeableDTO dto) {
        return ResponseEntity.ok(canjeableService.updateCanjeable(id, dto));
    }

    /**
     * ⭐ NUEVO: Eliminar (soft delete) un canjeable
     * Marca activo = false
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteCanjeable(@PathVariable Integer id) {
        return ResponseEntity.ok(canjeableService.deleteCanjeable(id));
    }
}