package com.example.facultades.dto;

import com.example.facultades.model.Respuesta;
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
public class RespuestaDTO extends BaseDTO<Respuesta> {
    private Long id;
    private String mensaje;
    private Date fecha;
    private List<RespuestaDTO> listaRespuesta;  // Relación recursiva de respuestas
    private List<ReaccionDTO> listaReaccion;
    private UsuarioDTO usuario;  // Se mapea el usuario asociado
}
