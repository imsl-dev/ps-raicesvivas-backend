package com.raicesvivas.backend.controllers.testControllers;

import com.raicesvivas.backend.models.dtos.mailDtos.EmailMultiRequestDto;
import com.raicesvivas.backend.models.dtos.mailDtos.EmailRequestDto;
import com.raicesvivas.backend.services.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/email")
@RequiredArgsConstructor
public class EmailController {
    private final EmailService emailService;

    @PostMapping("/sendSimple")
    public ResponseEntity<String> sendEmail(@RequestBody EmailRequestDto request) {
        try {
            emailService.enviarMail(request);
            return ResponseEntity.ok("Email enviado exitosamente");
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body("Error al enviar email: " + e.getMessage());
        }
    }

    @PostMapping("/sendMulti")
    public ResponseEntity<String> sendEmail(@RequestBody EmailMultiRequestDto request) {
        try {
            emailService.enviarMail(request);
            return ResponseEntity.ok("Email enviado exitosamente");
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body("Error al enviar email: " + e.getMessage());
        }
    }
}
