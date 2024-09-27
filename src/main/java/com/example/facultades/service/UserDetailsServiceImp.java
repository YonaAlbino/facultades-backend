package com.example.facultades.service;

import com.example.facultades.dto.AuthLoguinRequestDTO;
import com.example.facultades.dto.AuthLoguinResponseDTO;
import com.example.facultades.enums.DuracionToken;
import com.example.facultades.util.JwtUtil;
import com.example.facultades.model.Usuario;
import com.example.facultades.repository.IUsuarioRepository;
import com.example.facultades.util.Utili;
import jdk.jshell.execution.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImp implements UserDetailsService {

    @Autowired
    private IUsuarioRepository usuarioRepo;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepo.findUserEntityByusername(username).orElseThrow(
                () -> new UsernameNotFoundException("El usuario " +username+ " no fue encontrado"));

        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();

        usuario.getListaRoles()
                .stream()
                .forEach(roles -> authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(roles.getNombreRol()))));

        usuario.getListaRoles().stream()
                .flatMap(role -> role.getListaPermiso().stream())
                .forEach(permiso -> authorityList.add(new SimpleGrantedAuthority(permiso.getNombrePermiso())));

        return new User(
                usuario.getUsername(),
                usuario.getPassword(),
                usuario.isEnable(),
                usuario.isAccountNotExpired(),
                usuario.isAccountNotLocked(),
                usuario.isCredentialNotExpired(),
                authorityList);
    }

    public Authentication authenticate(String username, String password){
        UserDetails userDetails = this.loadUserByUsername(username);

        if(userDetails == null)
            throw new BadCredentialsException("Nombre de usuario incorrecto");

        if(!passwordEncoder.matches(password, userDetails.getPassword()))
            throw new BadCredentialsException("Contraseña incorrecta");

        return new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
    }

    public Authentication authenticate(String username){
        UserDetails userDetails = this.loadUserByUsername(username);

        if(userDetails == null) {
            throw new BadCredentialsException("Nombre de usuario incorrecto");
        }

        return new UsernamePasswordAuthenticationToken(username, userDetails.getPassword(), userDetails.getAuthorities());
    }

    public AuthLoguinResponseDTO loguin (AuthLoguinRequestDTO authLoguinRequestDTO){
        String username = authLoguinRequestDTO.nombreUsuario();
        String password = authLoguinRequestDTO.contrasenia();

        Authentication authentication = this.authenticate(username, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        Usuario usuario = usuarioRepo.findUserEntityByusername(username).get();
        String role = Utili.obtenerRol(authentication);

        String accesToken = jwtUtil.createToken(authentication, DuracionToken.ACCES_TOKEN.getDuracion());
        String refreshToekn = jwtUtil.createToken(authentication, DuracionToken.REFRESH_TOKEN.getDuracion());
        AuthLoguinResponseDTO authLoguinResponseDTO = new AuthLoguinResponseDTO(username, role, usuario.getId(),"Loguin correcto", accesToken, refreshToekn, true);
        return authLoguinResponseDTO;
    }

}
