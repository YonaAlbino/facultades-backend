package com.example.facultades.dto;

import com.example.facultades.model.Calificacion;
import com.example.facultades.model.Carrera;
import com.example.facultades.model.Comentario;
import com.example.facultades.model.Universidad;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CarreraDTO extends BaseDTO<Carrera> {
    private Long id;
    private String nombre;
    private String grado;
    private String duracion;
    private boolean activa;
    private List<ComentarioDTO> listaComentarios;
    private List<CalificacionDTO> listaCalificacion;

   /* @Override
    public BaseDTO convertirDTO(Carrera entidad) {
        return null;
    }

    @Override
    public Carrera convertirEntidad(BaseDTO dto) {
        return null;
    }*/
}
