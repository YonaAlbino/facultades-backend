package com.example.facultades.model;

import com.example.facultades.generics.BaseEntity;
import com.example.facultades.util.INotificable;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Universidad extends BaseEntity implements INotificable<Universidad> {
    /*@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;*/
    private String nombre;
    @Column(length = 1000) // Por ejemplo, aquí estamos especificando una longitud máxima de 1000 caracteres
    private String imagen;
    private String direccion;
    @Column(length = 2000) // Por ejemplo, aquí estamos especificando una longitud máxima de 1000 caracteres
    private String descripcion;
    private String direccionWeb;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Carrera> listaCarreras;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Calificacion> listaCalificacion;

    @OneToMany(cascade = CascadeType. ALL)
    private List<Comentario> listaComentarios;

    @Override
    @JsonIgnore
    public String getDetalleEvento() {
        return getNombre();
    }
}
