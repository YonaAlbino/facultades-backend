package com.example.facultades.excepciones;

public class UsuarioExistenteException extends RuntimeException{
    public UsuarioExistenteException(){
        super("El mail ya esta en uso");
    }
}
