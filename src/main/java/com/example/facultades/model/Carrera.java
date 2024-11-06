package com.example.facultades.model;

import com.example.facultades.generics.BaseEntity;
import com.example.facultades.util.INotificable;
import com.example.facultades.util.InotificarPropietario;
import com.example.facultades.util.ItipoEntidad;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class Carrera extends BaseEntity implements INotificable<Carrera>, InotificarPropietario<Carrera>, ItipoEntidad {
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

    @ManyToOne
    @JsonBackReference(value = "universidad-carrera")
    @JoinColumn(name = "universidad_id")
    private Universidad universidad;

    @Override
    @JsonIgnore
    public String getDetalleEvento() {
        return getNombre();
    }

    @Override
    public Long retornarPorpietario(Carrera entidad) {
        return this.universidad.retornarPorpietario(universidad);
    }

    @Override
    public Class<?> obtenerTipoClase() {
        return Carrera.class;
    }
}
