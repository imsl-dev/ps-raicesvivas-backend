package com.raicesvivas.backend.controllers;

import com.raicesvivas.backend.models.dtos.PeticionOrganizadorDTO;
import com.raicesvivas.backend.services.PeticionOrganizadorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/peticiones")
@RequiredArgsConstructor
public class PeticionController {

    private final PeticionOrganizadorService peticionService;

    @PostMapping()
    public ResponseEntity<PeticionOrganizadorDTO> enviarPeticion(@RequestBody PeticionOrganizadorDTO dto) {
        return ResponseEntity.ok(peticionService.)
    }
}
