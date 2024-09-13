package com.example.facultades.model;

import com.example.facultades.generics.BaseEntity;
import jakarta.persistence.Entity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Permiso extends BaseEntity  {
   /* @Id
    @GeneratedValue (strategy = GenerationType.SEQUENCE)
    private Long idPermiso;*/
    private String nombrePermiso;


}
