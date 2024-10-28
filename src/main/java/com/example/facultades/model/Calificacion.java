package com.example.facultades.model;

import com.example.facultades.generics.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class Calificacion extends BaseEntity {
    /*@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long idCalificacion;*/
    private Double nota;

}
