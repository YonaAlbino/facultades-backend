package com.example.facultades.dto;

import com.example.facultades.model.Reaccion;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReaccionDTO extends BaseDTO<Reaccion> {
    private Long id;
    private int meGusta;
    private  int noMegusta;
}
