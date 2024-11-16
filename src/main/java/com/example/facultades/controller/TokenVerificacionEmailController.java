package com.example.facultades.controller;

import com.example.facultades.dto.MensajeRetornoSimple;
import com.example.facultades.dto.TokenVerificacionEmailDTO;
import com.example.facultades.generics.ControllerGeneric;
import com.example.facultades.generics.IgenericService;
import com.example.facultades.model.TokenVerificacionEmail;
import com.example.facultades.service.IEmailService;
import com.example.facultades.service.ITokenVerificacionEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@RestController
@RequestMapping("/tokenVerificacionEmail")
public class TokenVerificacionEmailController extends ControllerGeneric<TokenVerificacionEmail, TokenVerificacionEmailDTO, Long> {
    @Autowired
    private ITokenVerificacionEmailService verificacionEmailService;

    @GetMapping("/actualizarToken/{id}")
    public ResponseEntity<MensajeRetornoSimple> actualizarTokenVerificacion(@PathVariable Long id) {
        verificacionEmailService.actualizarYEnviarToken(id);
        return ResponseEntity.ok(new MensajeRetornoSimple("Token de verificación actualizado y enviado con éxito"));

    }
}
