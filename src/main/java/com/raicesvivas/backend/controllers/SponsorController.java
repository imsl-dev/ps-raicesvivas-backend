package com.raicesvivas.backend.controllers;

import com.raicesvivas.backend.models.dtos.SponsorDto;
import com.raicesvivas.backend.models.entities.Sponsor;
import com.raicesvivas.backend.services.SponsorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/sponsors")
@RequiredArgsConstructor
public class SponsorController {

    private final SponsorService sponsorService;

    @GetMapping
    public ResponseEntity<List<Sponsor>> getAllSponsors() {
        List<Sponsor> sponsors = sponsorService.getAllSponsors();
        return ResponseEntity.ok(sponsors);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sponsor> getSponsorById(@PathVariable int id) {
        try {
            Sponsor sponsor = sponsorService.getSponsorById(id);
            return ResponseEntity.ok(sponsor);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Sponsor> createSponsor(@Valid @RequestBody SponsorDto sponsorDto) {
        try {
            Sponsor newSponsor = sponsorService.saveSponsor(sponsorDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(newSponsor);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Sponsor> updateSponsor(@PathVariable int id, @Valid @RequestBody SponsorDto sponsorDto) {
        try {
            // Asegurarse de que el DTO tenga el ID correcto
            sponsorDto.setId(id);
            Sponsor updatedSponsor = sponsorService.updateSponsor(sponsorDto);
            return ResponseEntity.ok(updatedSponsor);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSponsor(@PathVariable int id) {
        try {
            sponsorService.deleteSponsorById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}