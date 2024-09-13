package com.example.facultades.dto;

import com.example.facultades.model.Calificacion;
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
public class CarreraDto {
    private String nombre;
    private String grado;
    private String duracion;
    private Universidad universidad;
    private boolean activa =  true;

    @OneToMany()
    private List<Comentario> listaComentarios;

    @OneToMany()
    private List<Calificacion> listaCalificacion;
}
