package com.example.facultades.service;

import com.example.facultades.model.Comentario;

import java.util.List;
import java.util.Optional;

public interface IComentarioService  {
    public List<Comentario> findComentariosByUniversidadId (Long idUniversidad, int cantidadRegistros, int pagina);
    public List<Comentario> findComentariosByCarreraId (Long idCarrera, int cantidadRegistros, int pagina);

}
