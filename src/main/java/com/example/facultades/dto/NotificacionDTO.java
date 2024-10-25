package com.example.facultades.dto;

import com.example.facultades.model.Notificacion;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NotificacionDTO extends BaseDTO<Notificacion> {
    private Long id;
    private String informacion;
    private Long idRedireccionamiento;
    private Boolean leida;
    private List<UsuarioDTO>  listaUsuarios;
    private List<UsuarioLeidoDTO> listaDeusuariosLeidos;
}
