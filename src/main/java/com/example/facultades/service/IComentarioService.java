package com.example.facultades.service;

import com.example.facultades.dto.ComentarioDTO;
import com.example.facultades.model.Comentario;

import java.util.List;
import java.util.Optional;

public interface IComentarioService  {
    public List<ComentarioDTO> findComentariosByUniversidadId (Long idUniversidad, int cantidadRegistros, int pagina);
    public List<ComentarioDTO> findComentariosByCarreraId (Long idCarrera, int cantidadRegistros, int pagina);
   // public ComentarioDTO findComentariosByRespuestaRespuestaId(Long idRespuestaRespuesta);
}
