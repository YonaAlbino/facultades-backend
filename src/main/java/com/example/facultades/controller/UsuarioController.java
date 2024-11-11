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
import java.util.List;

@RestController
@RequestMapping("/usuario")
//@PreAuthorize("hasRole('ADMIN')")
public class UsuarioController extends ControllerGeneric<Usuario, UsuarioDTO, Long> {

    @Autowired
    private ITokenVerificacionEmailService verificacionEmailService;

    @Autowired
    private IgenericService<Usuario, Long> usuarioService;

    @GetMapping("/verificarEmail/{token}/{idTokenVerificador}")
    public void verifyAccount(@PathVariable String token, @PathVariable Long idTokenVerificador, HttpServletResponse response) throws IOException {
        TokenVerificacionEmail verificationToken = verificacionEmailService.findByToken(token);

        if (verificationToken == null) {
            // Token no encontrado
            System.out.println("Token no encontrado para el idTokenVerificador: " + idTokenVerificador);
            redirectWithError(response, idTokenVerificador);
            return;
        }

        if (verificationToken.getFechaExpiracion().isBefore(LocalDateTime.now())) {
            // Token expirado
            System.out.println("El token ha expirado para el idTokenVerificador: " + idTokenVerificador);
            redirectWithError(response, idTokenVerificador);
            return;
        }

        // Actualizar estado del usuario
        Usuario usuario = verificationToken.getUsuario();
        usuario.setEmailVerified(true);
        usuarioService.update(usuario);

        // Obtener el rol del usuario
        String role = obtenerRol(usuario);

        // Obtener el token y el ID del usuario
        String token2 = usuario.getRefreshToken().getToken();
        Long idUsuario = usuario.getId();

        // Construir URL de redirección
        String redirectUrl = "http://localhost:4200/?token=" + token2 + "&role=" + role + "&idUsuario=" + idUsuario;
        System.out.println("Redirigiendo a: " + redirectUrl);

        // Redirigir a la URL de destino
        response.setStatus(HttpServletResponse.SC_FOUND);
        response.setHeader("Location", redirectUrl);
    }

    // Método auxiliar para obtener el rol del usuario
    private String obtenerRol(Usuario usuario) {
        List<Rol> roles = usuario.getListaRoles();
        for (Rol rol : roles) {
            if ("ADMIN".equals(rol.getNombreRol()) || "USER".equals(rol.getNombreRol())) {
                return rol.getNombreRol();
            }
        }
        return "";  // En caso de no encontrar rol, retorna vacío (puedes ajustar esto si es necesario)
    }

    // Método para redirigir con error
    private void redirectWithError(HttpServletResponse response, Long idTokenVerificador) throws IOException {
        response.setStatus(HttpServletResponse.SC_FOUND);
        response.setHeader("Location", "http://localhost:4200/error/token/" + idTokenVerificador);
    }
}
