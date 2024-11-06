package com.example.facultades.model;

import com.example.facultades.generics.BaseEntity;
import com.example.facultades.util.INotificable;
import com.example.facultades.util.InotificarPropietario;
import com.example.facultades.util.ItipoEntidad;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class Universidad extends BaseEntity implements INotificable<Universidad>, InotificarPropietario<Universidad>, ItipoEntidad {
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
    @JsonManagedReference(value = "universidad-carrera")
    private List<Carrera> listaCarreras;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Calificacion> listaCalificacion;

    @OneToMany(cascade = CascadeType. ALL)
    private List<Comentario> listaComentarios;

    @ManyToOne
    @JsonBackReference(value = "usuario-universidad")
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @Override
    @JsonIgnore
    public String getDetalleEvento() {
        return getNombre();
    }

    @Override
    @JsonIgnore
    public Long retornarPorpietario(Universidad entidad) {
        return this.usuario.getId();
    }

    @Override
    @JsonIgnore
    public Class<?> obtenerTipoClase() {
        return Universidad.class;
    }
}
