package com.raicesvivas.backend.services;

import com.raicesvivas.backend.models.dtos.mailDtos.EmailMultiRequestDto;
import com.raicesvivas.backend.models.dtos.mailDtos.EmailRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class EmailService {
    @Value("${MAIL_USERNAME}")
    private String MAIL_USERNAME;
    private final JavaMailSender mailSender;

    public void enviarMail(EmailRequestDto emailRequestDto) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(MAIL_USERNAME); // Mail desde el cual se envía
            message.setTo(emailRequestDto.getEmailDestinatario());
            message.setSubject(emailRequestDto.getAsunto());
            message.setText(emailRequestDto.getTexto());

            mailSender.send(message);
            System.out.println("Email enviado exitosamente a: " + emailRequestDto.getEmailDestinatario());
        } catch (Exception e) {
            System.err.println("Error al enviar email: " + e.getMessage());
            throw e;
        }
    }

    // Método para enviar a múltiples destinatarios
    public void enviarMail(EmailMultiRequestDto emailRequestDto) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(MAIL_USERNAME);
            message.setBcc(emailRequestDto.getEmailsDestinatarios().toArray(new String[0]));
            message.setSubject(emailRequestDto.getAsunto());
            message.setText(emailRequestDto.getTexto());
            mailSender.send(message);
            System.out.println("Email enviado a múltiples destinatarios");
        } catch (Exception e) {
            System.err.println("Error al enviar email: " + e.getMessage());
            throw e;
        }
    }
}
