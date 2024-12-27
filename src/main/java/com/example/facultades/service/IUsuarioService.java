package com.example.facultades.service;



import com.example.facultades.dto.MensajeRetornoSimple;
import com.example.facultades.model.TokenVerificacionEmail;
import com.example.facultades.model.Usuario;

import java.util.List;
import java.util.Optional;

public interface IUsuarioService {
    public String encriptPassword(String password);
    public Usuario saveUserOauth(String email);
    public  Optional<Usuario> findUserEntityByusername(String username);
    List<Usuario> getUsuarioListByRole(String nombreRol);
    public String buscarUsuarioPorNombre(String username);
    public TokenVerificacionEmail generarTokenVerificacion(Usuario usuario);
    public void encriptarContrasenia(Usuario usuario);
    public void infraccionarUsuario(Long idUsuario);
    void cambiarContrasenia(Long idUsuario, String contrasenia) throws Exception;
    public void actualizarContrasenia(Long idUsuario, String nuevaContrasenia, String contraseniaActual);
}
