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
        EmailRequestDto e = new EmailRequestDto();
        e.setEmailDestinatario("juanpablobauza01@gmail.com");
//        e.setAsunto("Te damos la bienvenida a RaicesVivas!");
//        e.setTexto("Hola Juan Pablo! Tu usuario se ha creado exitosamente. Esperamos que disfrutes de esta pasión que es ayudar al mundo con nosotros!");
//        emailService.enviarMail(e); //////////
//        e.setAsunto("Felicitaciones! Ya sos organizador.");
//        e.setTexto("Bienvenido, Juan Pablo. Como organizador de eventos benéficos, tu responsabilidad principal es planificar y coordinar cada actividad para asegurar su éxito, gestionar recursos y voluntarios, mantener la transparencia en el uso de fondos y promover la participación de la comunidad para maximizar el impacto solidario.\n" +
//                "\n" +
//                "Felicitaciones por asumir este rol. Tu trabajo crea oportunidades reales de ayuda y demuestra un compromiso valioso con quienes más lo necesitan. Cada acción que impulsás tiene el potencial de generar cambios concretos, y tu dedicación será clave para inspirar a otros y alcanzar resultados significativos.");
//        emailService.enviarMail(e);
        e.setAsunto("Tu inscripción al evento Reforestación Sierras de Córdoba se realizó con éxito");
        e.setTexto("Tu inscripción al evento fue realizada con éxito.");
        emailService.enviarMail(e);
        return "pong";
    }

}
