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
        iEmailService.enviarEmail(emailDestinatario,asunto,mensaje);
        return  ResponseEntity.ok(new MensajeRetornoSimple("Email enviado con exito"));
    }


}
