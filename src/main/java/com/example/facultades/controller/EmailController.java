package com.example.facultades.controller;

import com.example.facultades.dto.EmailDtoContacto;
import com.example.facultades.service.IEmailService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
