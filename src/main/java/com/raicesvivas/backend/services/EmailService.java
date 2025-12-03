package com.raicesvivas.backend.services;

import com.raicesvivas.backend.models.dtos.mailDtos.EmailMultiRequestDto;
import com.raicesvivas.backend.models.dtos.mailDtos.EmailRequestDto;
import com.raicesvivas.backend.models.entities.Evento;
import com.raicesvivas.backend.models.entities.Usuario;
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

    private String emailDestinatoarioTest;
    private List<String> listaEmailsDestinatariosTest;

    private void enviarMail(EmailRequestDto emailRequestDto) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(MAIL_USERNAME); // Mail desde el cual se envía
            message.setTo(emailRequestDto.getEmailDestinatario());
            message.setSubject(emailRequestDto.getAsunto());
            message.setText(emailRequestDto.getTexto());

//TODO: DESCOMENTAR PARA ENVIAR MAILS            mailSender.send(message);
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
//TODO: DESCOMENTAR PARA ENVIAR MAILS             mailSender.send(message);
            System.out.println("Email enviado a múltiples destinatarios");
        } catch (Exception e) {
            System.err.println("Error al enviar email: " + e.getMessage());
            throw e;
        }
    }

    public void EnviarMailBienvienida(Usuario usuario){
        EmailRequestDto emailRequestDto = new EmailRequestDto();
        emailRequestDto.setEmailDestinatario(usuario.getEmail());
        emailRequestDto.setAsunto("¡Bienvenido a Raíces Vivas, "+ usuario.getNombre() +"! \uD83C\uDF31");
        emailRequestDto.setTexto("¡Hola "+ usuario.getNombre() +"!\n" +
                "Tu cuenta fue creada con éxito. Ahora sos parte de una comunidad que cree en el poder de ayudar.\n" +
                "Desde hoy podés explorar eventos, inscribirte como voluntario y empezar a generar impacto real en quienes más lo necesitan.\n" +
                "Cada pequeña acción cuenta, y la tuya puede cambiar vidas.\n" +
                "¡Te esperamos con los brazos abiertos!\n" +
                "— El equipo de Raíces Vivas");
        enviarMail(emailRequestDto);
    }

    public void EnviarMailNuevoOrganizador(Usuario usuario){
        EmailRequestDto emailRequestDto = new EmailRequestDto();
        emailRequestDto.setEmailDestinatario(usuario.getEmail());
        emailRequestDto.setAsunto("¡Felicitaciones "+ usuario.getNombre() +"! Ya sos organizador \uD83C\uDF89");
        emailRequestDto.setTexto("¡Hola "+ usuario.getNombre() +"!\n" +
                "Tu solicitud fue aprobada. Ahora sos oficialmente organizador de eventos en Raíces Vivas.\n" +
                "A partir de hoy podés crear eventos benéficos, convocar voluntarios y liderar iniciativas que transformen realidades.\n" +
                "Recordá que tu rol es clave: cada evento que organices es una oportunidad para inspirar a otros y generar un impacto positivo en la comunidad.\n" +
                "Ya podés acceder a tu Panel de Organizador y comenzar a crear.\n" +
                "¡Gracias por sumarte a esta misión!\n" +
                "— El equipo de Raíces Vivas");
        enviarMail(emailRequestDto);
    }

    public void EnviarMailConfirmacionInscripcion(Usuario usuario, Evento evento){
        EmailRequestDto emailRequestDto = new EmailRequestDto();
        emailRequestDto.setEmailDestinatario(usuario.getEmail());
        emailRequestDto.setAsunto(" ¡Ya sos parte de "+ evento.getNombre() +"! \uD83C\uDF31");
        emailRequestDto.setTexto(
                "¡Hola "+ usuario.getNombre() +"!\n" +
                "¡Qué alegría! Tu inscripción a "+ evento.getNombre() +" fue confirmada.\n" +
                "\uD83D\uDCC5 Fecha: "+ evento.getHoraInicio().toLocalDate() +"\n" +
                "\uD83D\uDCCD Lugar: "+ evento.getDireccion() +"\n" +
                "Gracias por sumarte a hacer la diferencia. ¡Te esperamos!\n" +
                "— El equipo de Raíces Vivas");
        enviarMail(emailRequestDto);
    }

    public void EnviarMailModificacionDeEvento(EmailMultiRequestDto emailRequestDto, Evento evento){
        emailRequestDto.setAsunto("Actualización importante sobre "+ evento.getNombre() +" \uD83D\uDCE2");
        emailRequestDto.setTexto("¡Hola! " +
                "Queremos avisarte que el evento "+ evento.getNombre() +" tuvo algunos cambios:\n" +
                "\uD83D\uDCC5 Nueva fecha/hora: "+ evento.getHoraInicio().toLocalDate() +"\n" +
                "\uD83D\uDCCD Nueva ubicación: "+ evento.getDireccion() +"\n" +
                "Por favor, tené en cuenta esta actualización para no perderte la actividad.\n" +
                "¡Gracias por tu comprensión y nos vemos pronto!\n" +
                "— El equipo de Raíces Vivas");
        enviarMail(emailRequestDto);
    }

    public void EnviarEmailConfirmacionCreacionEvento(Usuario usuario, Evento evento){
        EmailRequestDto emailRequestDto = new EmailRequestDto();
        emailRequestDto.setEmailDestinatario(usuario.getEmail());
        emailRequestDto.setAsunto(" ¡Tu evento ya está en marcha! \uD83D\uDE80");
        emailRequestDto.setTexto("¡Hola "+ usuario.getNombre() +"!\n" +
                "¡Excelente noticia! Tu evento "+ evento.getNombre() +" fue creado con éxito y ya está visible para toda la comunidad.\n" +
                "\uD83D\uDCC5 Fecha: "+ evento.getHoraFin().toLocalDate() +"\n" +
                "\uD83D\uDCCD Lugar: "+ evento.getDireccion() +"\n" +
                "Ahora comienza lo más emocionante: ver cómo tu iniciativa cobra vida y se llena de personas dispuestas a ayudar.\n" +
                "Podés seguir el progreso y gestionar inscripciones desde tu Panel de Organizador.\n" +
                "¡Gracias por crear oportunidades donde otros ven obstáculos!\n" +
                "— El equipo de Raíces Vivas");
        enviarMail(emailRequestDto);
    }

    public void EnviarMailEventoEnCurso(EmailMultiRequestDto emailRequestDto, Evento evento){
        emailRequestDto.setAsunto(" ¡"+ evento.getNombre() +" está comenzando! \uD83D\uDE80");
        emailRequestDto.setTexto("¡El momento llegó! El evento "+ evento.getNombre() +" ya está en curso.\n" +
                "\uD83D\uDCCD Lugar: "+ evento.getDireccion() +"\n" +
                "Tu participación hace la diferencia. ¡Gracias por ser parte de este cambio!\n" +
                "— El equipo de Raíces Vivas");
        enviarMail(emailRequestDto);
    }

    public void  EnviarMailConfirmacionCancelacionOrganizador(Usuario usuario, Evento evento){
        EmailRequestDto emailRequestDto = new EmailRequestDto();
        emailRequestDto.setEmailDestinatario(usuario.getEmail());
        emailRequestDto.setAsunto("Tu evento "+ evento.getNombre() +" fue cancelado");
        emailRequestDto.setTexto("Hola "+ usuario.getNombre() +",\n" +
                "Confirmamos que el evento "+ evento.getNombre() +" fue cancelado correctamente.\n" +
                "Ya notificamos a todos los inscriptos sobre esta novedad.\n" +
                "Si tenés alguna consulta, no dudes en contactarnos.\n" +
                "— El equipo de Raíces Vivas");
        enviarMail(emailRequestDto);
    }

    public void  EnviarMailConfirmacionCancelacionUsuarios(EmailMultiRequestDto emailRequestDto, Evento evento){
        emailRequestDto.setAsunto(" El evento "+ evento.getNombre() +" fue cancelado \uD83D\uDE14");
        emailRequestDto.setTexto("Hola,\n" +
                "Lamentamos informarte que el evento "+ evento.getNombre() +" fue cancelado por el organizador.\n" +
                "Sabemos que tenías ganas de participar, pero no te desanimes. ¡Hay muchas otras formas de seguir ayudando!\n" +
                "Te invitamos a explorar otros eventos disponibles en nuestra plataforma.\n" +
                "— El equipo de Raíces Vivas");
        enviarMail(emailRequestDto);
    }

}
