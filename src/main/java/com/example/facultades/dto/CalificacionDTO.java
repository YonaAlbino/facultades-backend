package com.example.facultades.dto;

import com.example.facultades.model.Calificacion;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CalificacionDTO extends BaseDTO<Calificacion> {
    private Long id;
    private Double nota;
}
