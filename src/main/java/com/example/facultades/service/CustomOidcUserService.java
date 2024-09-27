package com.example.facultades.service;


import com.example.facultades.enums.DuracionToken;
import com.example.facultades.util.JwtUtil;
import com.example.facultades.model.OidcUserPersonalizado;
import com.example.facultades.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class CustomOidcUserService extends OidcUserService {

    @Autowired
    private IUsuarioService usuarioUservice;

    @Autowired
    @Lazy
    private UserDetailsServiceImp userDetails;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        OidcUser oidcUser = super.loadUser(userRequest);
        String jwtToken;
        String refreshToken;
        String email = oidcUser.getEmail();
        Authentication authentication = null;
        Long idUsuaruio;
        String role = "";

        Optional<Usuario> usuario = usuarioUservice.findUserEntityByusername(email);

        if(usuario.isEmpty()){
            Usuario nuevoUsuario = usuarioUservice.saveUserOauth(email);
            authentication = userDetails.authenticate(nuevoUsuario.getUsername());
            idUsuaruio = nuevoUsuario.getId();
        }else{
            authentication = userDetails.authenticate(usuario.get().getUsername());
            idUsuaruio = usuario.get().getId();
        }
        jwtToken = jwtUtil.createToken(authentication, DuracionToken.ACCES_TOKEN.getDuracion());
        refreshToken = jwtUtil.createToken(authentication, DuracionToken.REFRESH_TOKEN.getDuracion());
        //System.out.println(authentication.getAuthorities());


        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        for (GrantedAuthority authority : authorities) {
            if(authority.getAuthority().equals("ROLE_ADMIN") || authority.getAuthority().equals("ROLE_USER") || authority.getAuthority().equals("ROLE_AUTOR") ) {
                //Validar que si un usuario tiene mas de un rol se guarde el rol con mayor permiso
                role = authority.getAuthority();
            }
        }

        //System.out.println(oidcUser.getGivenName());
        //System.out.println(oidcUser.getFamilyName());
        //System.out.println(oidcUser.getPicture());
        return new OidcUserPersonalizado(oidcUser, jwtToken, refreshToken, role, idUsuaruio);
    }

}
