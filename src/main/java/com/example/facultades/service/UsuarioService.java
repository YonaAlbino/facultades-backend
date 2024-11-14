package com.example.facultades.service;

import com.example.facultades.dto.BaseDTO;
import com.example.facultades.dto.UsuarioDTO;
import com.example.facultades.enums.DuracionToken;
import com.example.facultades.enums.MensajeNotificacionAdmin;
import com.example.facultades.enums.NombreRepositorio;
import com.example.facultades.enums.Socket;
import com.example.facultades.generics.GenericService;
import com.example.facultades.generics.IgenericService;
import com.example.facultades.model.*;
import com.example.facultades.repository.IUsuarioRepository;

import com.example.facultades.util.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.RandomStringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class UsuarioService extends GenericService<Usuario, Long> implements IUsuarioService, IEntidadAsociable<Usuario> {

    @Autowired
    private IAsociarEntidades asociarEntidades;

    @Autowired
    private IRepositoryFactory repositoryFactory;

    @Autowired
    private IUsuarioRepository usuarioRepo;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private INotificacionService notificacionService;

    @Autowired
    private IgenericService<RefreshToken, Long> refreshTokenService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    @Lazy
    private IRolService rolService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private IgenericService<TokenVerificacionEmail, Long> verificacionEmailService;

    @Autowired
    private IEmailService emailService;

    @Override
    public String encriptPassword(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }

    @Override
    public String buscarUsuarioPorNombre(String username){
        return usuarioRepo.buscarUsuarioPorNombre(username);
    }

    @Override
    @Transactional
    public Usuario saveUserOauth(String email) {
        Usuario usuario = new Usuario();
        usuario.setEmailVerified(true);
        usuario.setRefreshToken(crearRefreshToken(usuario));
        List<Rol> roleList = new ArrayList<>();
        Rol rolPorDefecto = rolService.findRolByName("ADMIN");
        Rol managedRole = entityManager.merge(rolPorDefecto);

        roleList.add(managedRole);

        usuario.setEnable(true);
        usuario.setAccountNotExpired(true);
        usuario.setAccountNotLocked(true);
        usuario.setCredentialNotExpired(true);
        usuario.setUsername(email);
        usuario.setPassword(this.encriptPassword(this.generatedPassword()));
        usuario.setListaRoles(roleList);
        return  usuarioRepo.save(usuario);
        //return this.save(usuario);
    }

    @Override
    public Optional<Usuario> findUserEntityByusername(String username) {
        //IUsuarioRepository IUsuarioRepository = (IUsuarioRepository) repositoryFactory.generarRepositorio(NombreRepositorio.USUARIO.getRepoName());
        return usuarioRepo.findUserEntityByusername(username);
    }

    @Override
    public List<Usuario> getUsuarioListByRole(String nombreRol) {
        return usuarioRepo.findUserEntityByListaRolesNombreRol(nombreRol);
    }

    public String generatedPassword(){
        return RandomStringUtils.randomAlphanumeric(8);
    }

    @Override
    public Usuario save(Usuario usuario) {
        configurarUsuario(usuario);
        asociarRoles(usuario);
        encriptarContrasenia(usuario);
        Usuario usuarioGuardado = guardarUsuario(usuario);
        if (usuarioGuardado.getId() != null) {
            manejarNotificacionCreacion(usuarioGuardado);
            // Generar y guardar el token de verificación
            TokenVerificacionEmail verificationToken = generarTokenVerificacion(usuarioGuardado);
            verificacionEmailService.save(verificationToken);

            // Enviar el correo de verificación
            enviarCorreoVerificacion(usuarioGuardado, verificationToken);
        }
        return usuarioGuardado;
    }

    private TokenVerificacionEmail generarTokenVerificacion(Usuario usuario) {
        String token = UUID.randomUUID().toString();
        TokenVerificacionEmail verificationToken = new TokenVerificacionEmail();
        verificationToken.setToken(token);
        verificationToken.setUsuario(usuario);
        verificationToken.setFechaExpiracion(LocalDateTime.now().plusMinutes(30));
        return verificationToken;
    }

    private void enviarCorreoVerificacion(Usuario usuario, TokenVerificacionEmail verificationToken) {
        // Enviar el correo de verificación con el token
        emailService.enviarCorreoVerificacionEmail(usuario.getUsername(), verificationToken.getToken(), verificationToken.getId());
    }

    private void configurarUsuario(Usuario usuario) {
        usuario.setEmailVerified(false);
        usuario.setEnable(true);
        usuario.setAccountNotExpired(true);
        usuario.setAccountNotLocked(true);
        usuario.setCredentialNotExpired(true);
        usuario.setRefreshToken(crearRefreshToken(usuario));
    }

    private void asociarRoles(Usuario usuario) {
        usuario.setListaRoles(Arrays.asList(rolService.findRolByName("ADMIN")));
        this.asociar(usuario);
    }

    private void encriptarContrasenia(Usuario usuario) {
        usuario.setPassword(this.encriptPassword(usuario.getPassword()));
    }

    private Usuario guardarUsuario(Usuario usuario) {
        return usuarioRepo.save(usuario);
    }

    private void manejarNotificacionCreacion(Usuario usuarioGuardado) {
        Notificacion notificacion = new Notificacion();
        notificacion.setUsuario(true);
        Utili.manejarNotificacionAdmin(MensajeNotificacionAdmin.CREACION_USUARIO.getNotificacion(), usuarioGuardado, notificacionService, notificacion);
    }
//xD
    public RefreshToken crearRefreshToken(Usuario usuario){
        String token = jwtUtil.createRefreshToken(usuario.getUsername(), DuracionToken.REFRESH_TOKEN.getDuracion());
        RefreshToken refreshToken = refreshTokenService.save(new RefreshToken(token));
        return refreshTokenService.save(refreshToken);
    }

    @Override
    public Usuario update(Usuario usuario) {
        this.asociar(usuario);
        return this.usuarioRepo.save(usuario);
    }

    @Override
    public BaseDTO<Usuario> convertirDTO(Usuario usuario) {
        UsuarioDTO usuarioDTO = modelMapper.map(usuario, UsuarioDTO.class);
        return usuarioDTO;
    }

    @Override
    public Usuario converirEntidad(BaseDTO<Usuario> DTO) {
        Usuario usuario = modelMapper.map(DTO, Usuario.class);
        return usuario;
    }

    @Override
    public void asociar(Usuario usuario) {
        usuario.setListaRoles(asociarEntidades.relacionar(usuario.getListaRoles(),repositoryFactory.generarRepositorio(NombreRepositorio.ROL.getRepoName())));
        usuario.setListaCalificacion(asociarEntidades.relacionar(usuario.getListaCalificacion(),repositoryFactory.generarRepositorio(NombreRepositorio.CALIFICACION.getRepoName())));
       // usuario.setListaRespuesta(asociarEntidades.relacionar(usuario.getListaRespuesta(), repositoryFactory.generarRepositorio(NombreRepositorio.RESPUESTA.getRepoName())));
        usuario.setListaReaccion(asociarEntidades.relacionar(usuario.getListaReaccion(),repositoryFactory.generarRepositorio(NombreRepositorio.REACCION.getRepoName())));
        usuario.setListaComentarios(asociarEntidades.relacionar(usuario.getListaComentarios(),repositoryFactory.generarRepositorio(NombreRepositorio.COMENTARIO.getRepoName())));
        usuario.setListaUniversidad(asociarEntidades.relacionar(usuario.getListaUniversidad(), repositoryFactory.generarRepositorio(NombreRepositorio.UNIVERSIDAD.getRepoName())));
    }

}
