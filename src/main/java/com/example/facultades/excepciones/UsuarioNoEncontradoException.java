package com.example.facultades.excepciones;

public class UsuarioNoEncontradoException extends Exception{

    public UsuarioNoEncontradoException(){
        super("El usuario que ingresaste no exite en la base de datos");
    }

}
