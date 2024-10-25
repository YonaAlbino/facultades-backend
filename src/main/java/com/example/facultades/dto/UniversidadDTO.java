package com.example.facultades.dto;

import com.example.facultades.model.Universidad;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UniversidadDTO extends BaseDTO<Universidad> {
    private Long id;  // Si BaseEntity tiene el campo "id"
    private String nombre;
    private String imagen;
    private String direccion;
    private String descripcion;
    private String direccionWeb;
    private List<CarreraDTO> listaCarreras;
    private List<CalificacionDTO> listaCalificacion;
    private List<ComentarioDTO> listaComentarios;
}
