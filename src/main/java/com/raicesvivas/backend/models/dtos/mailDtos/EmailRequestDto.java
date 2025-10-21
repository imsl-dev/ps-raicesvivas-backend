package com.raicesvivas.backend.models.dtos.mailDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailRequestDto {
    private String emailDestinatario;
    private String asunto;
    private String texto;
}
