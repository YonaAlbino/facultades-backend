package com.example.facultades.security.filtros;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.facultades.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;

public class JwtTokenValidator extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtTokenValidator(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String jwtToken = extractToken(request);
        if (jwtToken != null) {
            try {
                DecodedJWT decodedJWT = jwtUtil.validateToken(jwtToken);
                authenticateUser(decodedJWT);
            } catch (Exception ex) {
                handleException(response, ex);
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    /**
     * Extrae el token JWT del encabezado Authorization.
     */
    private String extractToken(HttpServletRequest request) {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }

    /**
     * Autentica al usuario basado en el token JWT decodificado.
     */
    private void authenticateUser(DecodedJWT decodedJWT) {
        String username = jwtUtil.extractUsername(decodedJWT);
        String authorities = jwtUtil.getSpecifClaim(decodedJWT, "authorities").asString();
        Collection<? extends GrantedAuthority> authorityList = AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);

        Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, authorityList);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    /**
     * Maneja excepciones y envía una respuesta de error al cliente.
     */
    private void handleException(HttpServletResponse response, Exception ex) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String errorMessage = String.format("{\"error\": \"Unauthorized\", \"message\": \"%s\"}", ex.getMessage());
        response.getWriter().write(errorMessage);
    }
}
