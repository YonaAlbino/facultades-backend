package com.example.facultades.dto;

import com.example.facultades.model.Comentario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;
import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ComentarioDTO extends BaseDTO<Comentario> {
    private Long id;
    private Date fecha;
    private String mensaje;
    private List<ReaccionDTO> listaReaccion;
    private List<RespuestaDTO> listaRespuesta;
    private UsuarioDTO usuario;
}
