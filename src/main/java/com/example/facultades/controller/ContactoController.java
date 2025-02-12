package com.example.facultades.controller;

import com.example.facultades.dto.ContactoRequest;
import com.example.facultades.dto.MensajeRetornoSimple;
import com.example.facultades.excepciones.CaptchaException;
import com.example.facultades.service.IEmailService;
import com.example.facultades.service.RecaptchaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/contacto")
public class ContactoController {

    @Autowired
    private RecaptchaService recaptchaService;

    @Autowired
    private IEmailService emailService;

    @PostMapping()
    public ResponseEntity<MensajeRetornoSimple> enviarMensaje(@RequestBody ContactoRequest contactoRequest){
        boolean isCaptchaValid = recaptchaService.verifyRecaptcha(contactoRequest.captchaToken());
        if (!isCaptchaValid) {
            throw new CaptchaException();
        }

        emailService.enviarMailContacto(contactoRequest.email(), contactoRequest.mensaje());
        return ResponseEntity.ok(new MensajeRetornoSimple("Correo enviado con exito"));
    }

}
