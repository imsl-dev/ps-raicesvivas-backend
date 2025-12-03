package com.raicesvivas.backend.controllers.testControllers;

import com.raicesvivas.backend.models.dtos.mailDtos.EmailRequestDto;
import com.raicesvivas.backend.services.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/ping")
@RequiredArgsConstructor
public class PingController {
    private final EmailService emailService;
    @GetMapping("")
    public String ping() {
        return "pong";
    }
}
