package com.example.facultades.model;

import com.example.facultades.generics.BaseEntity;
import com.example.facultades.util.INotificable;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class Usuario extends BaseEntity implements INotificable<Usuario> {
    /*@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long idUsuario;*/
    private String username;
    private String password;
    private boolean enable;
    private boolean accountNotExpired;
    private boolean accountNotLocked;
    private boolean credentialNotExpired;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "usuarios_roles", joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "rol_id"))
    private List<Rol> listaRoles = new ArrayList<>();

    @OneToMany()
    private List<Universidad> listaUniversidad;

    @OneToMany()
    private List<Calificacion> listaCalificacion;

    @OneToOne()
    private RefreshToken refreshToken;

    @OneToMany(mappedBy = "usuario")
    @JsonManagedReference(value = "usuario-comentario")
    private List<Comentario> listaComentarios;

    @OneToMany()
    @JsonManagedReference(value = "usuario-respuesta")
    private  List<Respuesta> listaRespuesta;

    @OneToMany()
    private List<Reaccion> listaReaccion;


    @Override
    @JsonIgnore
    public String getDetalleEvento() {
        return getUsername();
    }
}
