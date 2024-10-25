package com.example.facultades.dto;
import com.example.facultades.model.Usuario;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@NoArgsConstructor
public class UsuarioDTO extends BaseDTO<Usuario> {
        private Long id; // Asumiendo que BaseEntity tiene este campo
        private String username;
        private boolean enable;
        private RefreshTokenDto refreshToken;
        private List<RolDTO> listaRoles;
        private List<UniversidadDTO> listaUniversidad;
        private List<CalificacionDTO> listaCalificacion;
        private List<ComentarioDTO> listaComentarios;
        private List<RespuestaDTO> listaRespuesta;
        private List<ReaccionDTO> listaReaccion;
}
