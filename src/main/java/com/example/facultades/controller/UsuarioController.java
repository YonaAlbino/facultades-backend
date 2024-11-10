package com.example.facultades.controller;

import com.example.facultades.dto.UsuarioDTO;
import com.example.facultades.generics.ControllerGeneric;
import com.example.facultades.generics.IgenericService;
import com.example.facultades.model.Rol;
import com.example.facultades.model.TokenVerificacionEmail;
import com.example.facultades.model.Usuario;
import com.example.facultades.service.ITokenVerificacionEmailService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/usuario")
//@PreAuthorize("hasRole('ADMIN')")
public class UsuarioController extends ControllerGeneric<Usuario, UsuarioDTO, Long> {

    @Autowired
    private ITokenVerificacionEmailService verificacionEmailService;

    @Autowired
    private IgenericService<Usuario, Long> usuarioService;

    @GetMapping("/verificarEmail/{token}")
    public void verifyAccount(@PathVariable String token, HttpServletResponse response) throws IOException {
        TokenVerificacionEmail verificationToken = verificacionEmailService.findByToken(token);

        if (verificationToken == null) {
            // Si el token es inválido, redirige a una página de error
            response.setStatus(HttpServletResponse.SC_FOUND);
            response.setHeader("Location", "http://localhost:4200/error/token-invalido");
            return;
        }

        if (verificationToken.getFechaExpiracion().isBefore(LocalDateTime.now())) {
            // Si el token ha expirado, redirige a una página de error
            response.setStatus(HttpServletResponse.SC_FOUND);
            response.setHeader("Location", "http://localhost:4200/error/token-expirado");
            return;
        }

        Usuario usuario = verificationToken.getUsuario();
        usuario.setEmailVerified(true);
        usuarioService.update(usuario);
        String role = "";
        for (Rol rol : usuario.getListaRoles()){
            if(rol.getNombreRol() == "ADMIN"){
                role = rol.getNombreRol();
                return;
            }
            if(rol.getNombreRol() == "USER"){
                role = rol.getNombreRol();
                return;
            }
        }

        // Redirige a la página de inicio de sesión o página principal con los parámetros del token, rol y ID del usuario.
        String redirectUrl = "http://localhost:4200/?token=" + usuario.getRefreshToken() + "&role=" + role + "&idUsuario=" + usuario.getId();
        response.setStatus(HttpServletResponse.SC_FOUND);
        response.setHeader("Location", redirectUrl);
    }
}
