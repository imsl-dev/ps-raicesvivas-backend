package com.raicesvivas.backend.controllers;

import com.raicesvivas.backend.models.dtos.MutarEstadoPeticionDTO;
import com.raicesvivas.backend.models.dtos.PeticionOrganizadorDTO;
import com.raicesvivas.backend.models.entities.PeticionOrganizador;
import com.raicesvivas.backend.services.PeticionOrganizadorService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/peticiones")
@RequiredArgsConstructor
public class PeticionController {

    private final PeticionOrganizadorService peticionService;

    @PostMapping()
    public ResponseEntity<PeticionOrganizador> enviarPeticion(@RequestBody PeticionOrganizadorDTO dto)
            throws BadRequestException {
        return ResponseEntity.ok(peticionService.postPeticion(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PeticionOrganizador> getPeticionByUserId(@PathVariable Integer id) {
        return ResponseEntity.ok(peticionService.getPeticionByUserId(id));
    }

    @PutMapping()
    public ResponseEntity<PeticionOrganizador> mutarEstadoPeticion(@RequestBody MutarEstadoPeticionDTO dto) {
        return ResponseEntity.ok(peticionService.mutarEstadoPeticion(dto.getPeticionId(),dto.getNuevoEstado()));
    }

    @GetMapping()
    public ResponseEntity<List<PeticionOrganizador>> getAllPeticiones() {
        return ResponseEntity.ok(peticionService.getAll());
    }
}
