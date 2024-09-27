package com.example.facultades.controller;


import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.facultades.dto.AccesToken;
import com.example.facultades.dto.AuthLoguinRequestDTO;
import com.example.facultades.dto.AuthLoguinResponseDTO;
import com.example.facultades.enums.DuracionToken;
import com.example.facultades.repository.IUsuarioRepository;
import com.example.facultades.service.UserDetailsServiceImp;
import com.example.facultades.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.parser.Entity;
import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthController {

    @Autowired
    private UserDetailsServiceImp userDetails;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private IUsuarioRepository iUsuarioRepository;

    @PostMapping("/loguin/password")
    public ResponseEntity<AuthLoguinResponseDTO> loguin(@RequestBody AuthLoguinRequestDTO authLoguinRequestDTO){
        return ResponseEntity.ok(userDetails.loguin(authLoguinRequestDTO));
    }

    @GetMapping("/login")
    @PreAuthorize("isAuthenticated()")
    @ResponseBody
    public void  loguinOauth(){
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @PostMapping("/getAccesToken")
    public ResponseEntity<AuthLoguinResponseDTO> getAccesToken(@RequestBody String refreshToken){
        System.out.println(refreshToken);

        DecodedJWT decodedJWT = jwtUtil.validateToken(refreshToken);
        String userName = jwtUtil.extractUsername(decodedJWT);
        String authoritues = jwtUtil.getSpecifClaim(decodedJWT, "authorities").asString();
        String nuevoAccesToken =  jwtUtil.createToken(userName,authoritues, 60*2000);
        Long idUsuario = iUsuarioRepository.findUserEntityByusername(userName).get().getId();
        AuthLoguinResponseDTO authLoguinResponseDTO = new AuthLoguinResponseDTO(userName, authoritues, idUsuario,"Loguin correcto", nuevoAccesToken, refreshToken, true);
        return ResponseEntity.ok(authLoguinResponseDTO);
    }
}
