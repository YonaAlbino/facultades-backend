package com.example.facultades.dto;

import com.example.facultades.model.Comentario;

import java.util.Date;

public class ComentarioDto {
    private Long id;
    private Date fecha;
    private String mensaje;
    private Long usuarioId;

    public ComentarioDto(Comentario comentario) {
        this.id = comentario.getId();
        this.fecha = comentario.getFecha();
        this.mensaje = comentario.getMensaje();
        this.usuarioId = comentario.getUsuario().getId();
    }
}
