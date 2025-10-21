package com.raicesvivas.backend.controllers;

import com.raicesvivas.backend.models.entities.auxiliar.Provincia;
import com.raicesvivas.backend.repositories.auxiliar.ProvinciaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/provincias")
@RequiredArgsConstructor
public class ProvinciaController {

    private final ProvinciaRepository provinciaRepository;

    @GetMapping
    private ResponseEntity<List<Provincia>> getProvincias() {
        return ResponseEntity.ok(provinciaRepository.findAll());
    }
}
