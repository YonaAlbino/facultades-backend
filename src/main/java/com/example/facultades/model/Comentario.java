package com.example.facultades.model;

import com.example.facultades.generics.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Comentario extends BaseEntity  {
   /* @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;*/
    private Date fecha;
    @Column(length = 1000) // Por ejemplo, aquí estamos especificando una longitud máxima de 1000 caracteres
    private String mensaje;

    @OneToMany()
    private List<Reaccion> listaReaccion;

    @OneToMany()
    private List<Respuesta> listaRespuesta;


}
