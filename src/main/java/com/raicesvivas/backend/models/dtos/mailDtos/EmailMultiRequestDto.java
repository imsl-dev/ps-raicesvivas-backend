package com.raicesvivas.backend.models.dtos.mailDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailMultiRequestDto {
    private List<String> emailsDestinatarios;
    private String asunto;
    private String texto;
}
