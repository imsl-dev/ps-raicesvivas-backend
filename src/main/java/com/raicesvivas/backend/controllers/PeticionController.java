package com.raicesvivas.backend.controllers;

import com.raicesvivas.backend.models.dtos.PeticionOrganizadorDTO;
import com.raicesvivas.backend.models.entities.PeticionOrganizador;
import com.raicesvivas.backend.services.PeticionOrganizadorService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
