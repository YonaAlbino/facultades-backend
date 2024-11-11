package com.example.facultades.service;

import com.example.facultades.dto.EmailDtoContacto;
import jakarta.mail.MessagingException;


public interface IEmailService {

    public void sendMail(EmailDtoContacto email) throws MessagingException;
    void enviarCorreoVerificacionEmail(String email, String token, Long idTokenVerificador);

}
