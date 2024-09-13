package com.example.facultades.model;

import com.example.facultades.generics.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Respuesta extends BaseEntity  {
    /*@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;*/
    private String mensaje;
    private Date fecha;

    @OneToMany()
    private List<Respuesta> listaRespuesta = new ArrayList<Respuesta>();

    @OneToMany()
    private List<Reaccion> listaReaccion;


}
