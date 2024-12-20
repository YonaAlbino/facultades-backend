package com.example.facultades.controller;

import com.example.facultades.dto.MensajeRetornoSimple;
import com.example.facultades.dto.RegistroRequest;
import com.example.facultades.dto.UsuarioDTO;
import com.example.facultades.excepciones.UsuarioExistenteException;
import com.example.facultades.excepciones.UsuarioNoEncontradoException;
import com.example.facultades.generics.ControllerGeneric;
import com.example.facultades.generics.IgenericService;
import com.example.facultades.model.Rol;
import com.example.facultades.model.TokenVerificacionEmail;
import com.example.facultades.model.Usuario;
import com.example.facultades.service.IEmailService;
import com.example.facultades.service.ITokenVerificacionEmailService;
import com.example.facultades.service.IUsuarioService;
import com.example.facultades.service.RecaptchaService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuario")
//@PreAuthorize("hasRole('ADMIN')")
public class UsuarioController extends ControllerGeneric<Usuario, UsuarioDTO, Long> {

    @Autowired
    private ITokenVerificacionEmailService verificacionEmailService;

    @Autowired
    private IgenericService<Usuario, Long> genericUsuarioService;

    @Autowired
    private IUsuarioService usuarioService;

    @Autowired
    private RecaptchaService recaptchaService;

    @PostMapping("/cambiarContrasenia")
    public ResponseEntity<MensajeRetornoSimple> cambiarContrasenia(@RequestParam Long idUsuario, @RequestParam String contrasenia){
        usuarioService.cambiarContrasenia(idUsuario, contrasenia);
        return ResponseEntity.ok(new MensajeRetornoSimple("Contraseña actualizada exitosamente"));
    }

    @PostMapping("/registro")
    public ResponseEntity<MensajeRetornoSimple> save(@RequestBody RegistroRequest registroRequest)  {

        boolean isCaptchaValid = recaptchaService.verifyRecaptcha(registroRequest.captchaToken());

        if (!isCaptchaValid) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MensajeRetornoSimple("El captcha token es invalido"));
        }

        String usuarioBuscado = usuarioService.buscarUsuarioPorNombre(registroRequest.email());
        if(usuarioBuscado == null){
            Usuario usuario = new Usuario();
            usuario.setUsername(registroRequest.email());
            usuario.setPassword(registroRequest.contrasenia());
            genericUsuarioService.save(usuario);
            return ResponseEntity.status(HttpStatus.CREATED).body(new MensajeRetornoSimple("El usuario fue creado"));
        }else throw new UsuarioExistenteException();

    }


    @GetMapping("/verificarEmail/{token}/{idTokenVerificador}")
    public void verifyAccount(@PathVariable String token, @PathVariable Long idTokenVerificador, HttpServletResponse response) throws IOException {
        TokenVerificacionEmail verificationToken = verificacionEmailService.findByToken(token);

        if (verificationToken == null) {
            // Token no encontrado
            redirectWithError(response, idTokenVerificador);
            return;
        }

        if (verificationToken.getFechaExpiracion().isBefore(LocalDateTime.now())) {
            // Token expirado
            redirectWithError(response, idTokenVerificador);
            return;
        }

        // Actualizar estado del usuario
        Usuario usuario = verificationToken.getUsuario();
        usuario.setEmailVerified(true);
        genericUsuarioService.update(usuario);

        // Obtener el rol del usuario
        String role = obtenerRol(usuario);

        // Obtener el token y el ID del usuario
        String token2 = usuario.getRefreshToken().getToken();
        Long idUsuario = usuario.getId();

        // Construir URL de redirección
        String redirectUrl = "http://localhost:4200/?token=" + token2 + "&role=" + role + "&idUsuario=" + idUsuario;

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
        response.setHeader("Location", "http://localhost:4200/error/token/" + idTokenVerificador + "?email=" + true + "&contrasenia=" + false);
    }
}
