package com.example.facultades.model;

import com.example.facultades.generics.BaseEntity;
import com.example.facultades.util.INotificable;
import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonBackReference;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Respuesta extends BaseEntity   {
    /*@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;*/
    private String mensaje;
    private Date fecha;

    @OneToMany()
    private List<Respuesta> listaRespuesta = new ArrayList<Respuesta>();

    @OneToMany()
    private List<Reaccion> listaReaccion;

    @ManyToOne
    @JsonBackReference(value = "usuario-respuesta")
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

}
