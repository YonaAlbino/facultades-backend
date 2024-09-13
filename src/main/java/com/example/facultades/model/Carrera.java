package com.example.facultades.model;

import com.example.facultades.generics.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Carrera extends BaseEntity{
    /*@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;*/
    private String nombre;
    private String grado;
    private String duracion;
    private boolean activa =  true;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Comentario> listaComentarios;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Calificacion> listaCalificacion;


}
