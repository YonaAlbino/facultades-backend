package com.example.facultades.dto;

import com.example.facultades.model.Rol;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RolDTO extends BaseDTO<Rol> {
    private Long id;
    private String nombreRol;
    private List<PermisoDTO> listaPermiso;
}
