package com.example.facultades.controller;


import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.facultades.dto.AccesToken;
import com.example.facultades.dto.AuthLoguinRequestDTO;
import com.example.facultades.dto.AuthLoguinResponseDTO;
import com.example.facultades.enums.DuracionToken;
import com.example.facultades.model.Usuario;
import com.example.facultades.repository.IUsuarioRepository;
import com.example.facultades.service.UserDetailsServiceImp;
import com.example.facultades.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.parser.Entity;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class AuthController {

    @Autowired
    private UserDetailsServiceImp userDetails;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private IUsuarioRepository iUsuarioRepository;

    @Autowired
    private UserDetailsServiceImp userDetailsServiceImp;

    @PostMapping("/loguin/password")
    public ResponseEntity<AuthLoguinResponseDTO> loguin(@RequestBody AuthLoguinRequestDTO authLoguinRequestDTO){
        return ResponseEntity.ok(userDetails.loguin(authLoguinRequestDTO));
    }

    @GetMapping("/login")
    @PreAuthorize("isAuthenticated()")
    @ResponseBody
    public void  loguinOauth(){
    }

    /*@PreAuthorize("hasRole('REFRESH')")
    @PostMapping("/getAccesToken")
    public ResponseEntity<AuthLoguinResponseDTO> getAccesToken(@RequestBody String refreshToken) {
        System.out.println(refreshToken);

        DecodedJWT decodedJWT = jwtUtil.validateToken(refreshToken);
        String userName = jwtUtil.extractUsername(decodedJWT);

        Authentication authentication = userDetailsServiceImp.authenticate(userName);
        String nuevoAccesToken = jwtUtil.createToken(authentication, 60 * 2000);
        DecodedJWT nuevoAccesTokenDecode = jwtUtil.validateToken(nuevoAccesToken);
        String authoritues = jwtUtil.getSpecifClaim(nuevoAccesTokenDecode, "authorities").asString();

        Long idUsuario = iUsuarioRepository.findUserEntityByusername(userName).get().getId();

        AuthLoguinResponseDTO authLoguinResponseDTO = new AuthLoguinResponseDTO(userName, authoritues, idUsuario,"Loguin correcto", nuevoAccesToken, true);
        return ResponseEntity.ok(authLoguinResponseDTO);
    }*/

    @PreAuthorize("hasRole('REFRESH')")
    @PostMapping("/getAccesToken")
    public ResponseEntity<AuthLoguinResponseDTO> getAccesToken(@RequestBody String refreshToken) {

        DecodedJWT decodeJWTRefreshToken = jwtUtil.validateToken(refreshToken);

        String userName = jwtUtil.extractUsername(decodeJWTRefreshToken);
        Authentication authentication = userDetailsServiceImp.authenticate(userName);

        String nuevoAccesToken = createAccessToken(authentication);
        String authorities  = jwtUtil.extractauthorities(nuevoAccesToken);

        Long idUsuario = this.obtenerIdUsuario(userName);

        // Crear la respuesta DTO
        AuthLoguinResponseDTO authLoguinResponseDTO = new AuthLoguinResponseDTO(
                userName, authorities, idUsuario, "Login correcto", nuevoAccesToken, true);

        return ResponseEntity.ok(authLoguinResponseDTO);
    }

    public Long obtenerIdUsuario(String userName){
        Optional<Usuario> usuarioOptional = iUsuarioRepository.findUserEntityByusername(userName);
        if (usuarioOptional.isPresent()) {
            return usuarioOptional.get().getId();
        } else {
            throw new UsernameNotFoundException("Usuario no encontrado");
        }
    }

    private String createAccessToken(Authentication authentication) {
        // Lógica para crear un nuevo token de acceso
        return jwtUtil.createToken(authentication, 60 * 2000);
    }

}
