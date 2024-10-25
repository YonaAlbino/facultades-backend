package com.example.facultades.model;

import com.example.facultades.generics.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
public class Notificacion extends BaseEntity {
    /*@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private  Long id;*/
    private String informacion;
    private Long idRedireccionamiento;
    private Boolean leida;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "notificaciones_usuarios", joinColumns = @JoinColumn(name = "notificacion_id"),
            inverseJoinColumns = @JoinColumn(name = "usuario_id"))
    private List<Usuario> listaUsuarios = new ArrayList<>();

    @OneToMany()
    private List<UsuarioLeido> listaDeusuariosLeidos = new ArrayList<>();
}
