package com.example.facultades.controller;

import com.example.facultades.dto.MensajeRetornoSimple;
import com.example.facultades.dto.TokenRecuperacionContraseniaDTO;
import com.example.facultades.enums.DuracionToken;
import com.example.facultades.excepciones.UsuarioNoEncontradoException;
import com.example.facultades.generics.ControllerGeneric;
import com.example.facultades.generics.IgenericService;
import com.example.facultades.model.Rol;
import com.example.facultades.model.TokenRecuperacionContrasenia;
import com.example.facultades.model.TokenVerificacionEmail;
import com.example.facultades.model.Usuario;
import com.example.facultades.service.*;
import com.example.facultades.util.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/TokenRecuperacionContrasenia")
public class TokenRecuperacionContraseniaController  extends ControllerGeneric<TokenRecuperacionContrasenia, TokenRecuperacionContraseniaDTO, Long> {

    @Autowired
    private IEmailService emailService;

    @Autowired
    private IUsuarioService usuarioService;

    @Autowired
    private IgenericService<TokenRecuperacionContrasenia, Long> genericTokenRecuperacionContrasenia;

    @Autowired
    private ITokenRecuperacionContraseniaService tokenRecuperacionContraseniaService;

    @Autowired
    private IgenericService<Usuario, Long> genericUsuarioService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsServiceImp userDetailsServiceImp;



    @GetMapping("/actualizarToken/{id}")
    public ResponseEntity<MensajeRetornoSimple> actualizarTokenVerificacion(@PathVariable Long id) {
        tokenRecuperacionContraseniaService.actualizarYEnviarToken(id);
        return ResponseEntity.ok(new MensajeRetornoSimple("Token de verificación actualizado y enviado con éxito"));

    }


    @PostMapping("/recuperarContraseña/{email}")
    public ResponseEntity<MensajeRetornoSimple> recuperarContrasenia(@PathVariable String email){
        Optional<Usuario> usuario = usuarioService.findUserEntityByusername(email);

        if(usuario.isEmpty())
            throw  new UsuarioNoEncontradoException();

        TokenRecuperacionContrasenia tokenRecuperacionContrasenia = null;
        if(usuario.get().getTokenRecuperacionContrasenia() != null){
            tokenRecuperacionContrasenia = tokenRecuperacionContraseniaService.actualizarToken(usuario.get().getTokenRecuperacionContrasenia());
            System.out.println("se actualizo");
        }else{
            tokenRecuperacionContrasenia = tokenRecuperacionContraseniaService.generarTokenVerificacion(usuario.get());

        }
        emailService.enviarCorreoRecuperacionContrasena(usuario.get().getUsername(),tokenRecuperacionContrasenia.getToken(), tokenRecuperacionContrasenia.getId());
        return  ResponseEntity.ok(new MensajeRetornoSimple("Correo de recuperación enviado"));
    }



    @GetMapping("/reestablecerContrasenia/{token}/{idTokenVerificador}")
    public void reestablecerContrasenia(@PathVariable String token, @PathVariable Long idTokenVerificador, HttpServletResponse response) throws IOException {
        TokenRecuperacionContrasenia verificationToken = tokenRecuperacionContraseniaService.findByToken(token);
        if (verificationToken == null) {
            // Token no encontrado
            redirectWithError(response, idTokenVerificador);
            System.out.println("TOKEN NULL");
            return;
        }

        if (verificationToken.getFechaExpiracion().isBefore(LocalDateTime.now())) {
            // Token expirado
            redirectWithError(response, idTokenVerificador);
            System.out.println("token invalido");
            return;
        }

        // Actualizar estado del usuario
        Usuario usuario = verificationToken.getUsuario();

        // Obtener el rol del usuario
        String role = obtenerRol(usuario);
        Long idUsuario = usuario.getId();
        Authentication authentication = userDetailsServiceImp.authenticate(usuario.getUsername());
        String nuevoAccesToken = createAccessToken(authentication);


        // Construir URL de redirección
        String redirectUrl = "http://localhost:4200/reestablecerContrasenia?token=" + nuevoAccesToken + "&role=" + role + "&idUsuario=" + idUsuario;
        // System.out.println("Redirigiendo a: " + redirectUrl);

        // Redirigir a la URL de destino
        response.setStatus(HttpServletResponse.SC_FOUND);
        response.setHeader("Location", redirectUrl);
    }

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
        response.setHeader("Location", "http://localhost:4200/error/token/" + idTokenVerificador + "?email=" + false + "&contrasenia=" + true);
    }

    private String createAccessToken(Authentication authentication) {
        // Lógica para crear un nuevo token de acceso
        return jwtUtil.createToken(authentication, 60 * 2000);
    }

}
