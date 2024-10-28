package com.example.facultades.dto;

import com.example.facultades.model.Notificacion;
import lombok.*;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class NotificacionDTO extends BaseDTO<Notificacion> {

    private String informacion;
    private Long idRedireccionamiento;
    private Boolean leida;
    private List<UsuarioDTO>  listaUsuarios;
    private List<UsuarioLeidoDTO> listaDeusuariosLeidos;
}
