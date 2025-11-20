package com.raicesvivas.backend.controllers;


import com.raicesvivas.backend.models.dtos.NuevoCanjeableDTO;
import com.raicesvivas.backend.models.entities.Canjeable;
import com.raicesvivas.backend.services.CanjeableService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/canjeables")
@RequiredArgsConstructor
public class CanjeableController {

    private final CanjeableService canjeableService;

    @PostMapping()
    public ResponseEntity<Canjeable> postCanjeable(@RequestBody NuevoCanjeableDTO dto) {
        System.out.println("Controller: " + dto.toString());
        return ResponseEntity.ok(canjeableService.postCanjeable(dto));
    }
}
