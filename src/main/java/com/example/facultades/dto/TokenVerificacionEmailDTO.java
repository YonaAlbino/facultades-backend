package com.example.facultades.dto;

import com.example.facultades.model.TokenVerificacionEmail;
import com.example.facultades.model.Usuario;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class TokenVerificacionEmailDTO extends BaseDTO<TokenVerificacionEmail>{
    private String  token;
    private LocalDateTime fechaExpiracion;
    private Long usuarioId;
}
