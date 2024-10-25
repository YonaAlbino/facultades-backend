package com.example.facultades.dto;

import com.example.facultades.model.UsuarioLeido;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioLeidoDTO extends BaseDTO<UsuarioLeido> {
    private Long id;
    private Long idUsuario;
}
