package com.example.facultades.controller;

import com.example.facultades.dto.EmailDtoContacto;
import com.example.facultades.dto.MensajeRetornoSimple;
import com.example.facultades.service.IEmailService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/email")
public class EmailController {

    @Autowired
    private IEmailService iEmailService;

    @PostMapping()
    public ResponseEntity<String> sendEmail(@RequestBody EmailDtoContacto email) throws MessagingException {
        iEmailService.sendMail(email);
        return new ResponseEntity<>("{\"mensaje\": \"Email enviado con éxito\"}", HttpStatus.OK);
    }

    @PostMapping("/enviar-emal-nueva-contrasenia")
    public ResponseEntity<MensajeRetornoSimple> enviarEmail(@RequestParam String emailDestinatario,
                                                            @RequestParam String asunto,
                                                            @RequestParam String mensaje)
                                              throws MessagingException {
        String mensajeHtml = "<div style='font-family: Arial, sans-serif; color: #333; padding: 20px; border: 1px solid #ddd; border-radius: 5px; text-align: center;'>"
                + "<h2 style='color: #0066cc;'>Cambio de Contraseña en FacusArg</h2>"
                + "<h3 style='color: #0066cc;'>Notificación de Cambio de Contraseña</h3>"
                + "<p>Tu contraseña ha sido cambiada exitosamente. Si no fuiste tú quien realizó este cambio, por favor, comunícate con el soporte inmediatamente.</p>"
                + "<p>A continuación, se detallan los cambios realizados:</p>"
                + "<p style='background: #f4f4f4; padding: 10px; border-radius: 5px; word-break: break-all; text-align: center;'>"
                + mensaje + "</p>"
                + "<p style='color: #777;'>Si no realizaste este cambio, por favor, contacta a nuestro equipo de soporte lo antes posible.</p>"
                + "<p style='color: #777;'>Este es un mensaje automático. No es necesario responder.</p>"
                + "</div>";

        iEmailService.enviarEmail(emailDestinatario,asunto,mensajeHtml);
        return  ResponseEntity.ok(new MensajeRetornoSimple("Email enviado con exito"));
    }


}
