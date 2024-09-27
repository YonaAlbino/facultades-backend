package com.example.facultades.controller;


import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.facultades.excepciones.*;
import com.example.facultades.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> manejadorExcepcionesGlobal(Exception ex) {

        ErrorResponse error = ErrorResponse.builder()
                .mensaje(ex.getMessage())
                .build();
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(UsuarioNoEncontradoException.class)
    public ResponseEntity<ErrorResponse> usuarioNoEncontradoException(UsuarioNoEncontradoException ex) {

        ErrorResponse error = ErrorResponse.builder()
                .mensaje(ex.getMessage())
                .build();
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EmailNoEncontradoException.class)
    public ResponseEntity<ErrorResponse> emailNoEncontrado(EmailNoEncontradoException ex) {

        ErrorResponse error = ErrorResponse.builder()
                .mensaje(ex.getMessage())
                .build();
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ContrasenaNoEncontradaException.class)
    public ResponseEntity<ErrorResponse> contrasenaIncorrecta(ContrasenaNoEncontradaException ex) {

        ErrorResponse error = ErrorResponse.builder()
                .mensaje(ex.getMessage())
                .build();
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UniversidadRepetidaException.class)
    public ResponseEntity<ErrorResponse> universidadRepetida(UniversidadRepetidaException ex) {

        ErrorResponse error = ErrorResponse.builder()
                .mensaje(ex.getMessage())
                .build();
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(RegistroExistenteException.class)
    public ResponseEntity<ErrorResponse> universidadRepetida(RegistroExistenteException ex) {

        ErrorResponse error = ErrorResponse.builder()
                .mensaje(ex.getMessage())
                .build();
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }




}
