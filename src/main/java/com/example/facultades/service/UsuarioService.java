package com.example.facultades.service;

import com.example.facultades.enums.NombreRepositorio;
import com.example.facultades.generics.GenericService;
import com.example.facultades.model.Rol;
import com.example.facultades.model.Usuario;
import com.example.facultades.repository.IUsuarioRepository;

import com.example.facultades.util.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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
    @Lazy
    private IRolService rolService;

    @Override
    public String encriptPassword(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }

    @Override
    @Transactional
    public Usuario saveUserOauth(String email) {
        Usuario usuario = new Usuario();
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

        return this.save(usuario);
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
        this.asociar(usuario);
        return this.usuarioRepo.save(usuario);
    }

    @Override
    public Usuario update(Usuario usuario) {
        return this.save(usuario);
    }

    @Override
    public void asociar(Usuario usuario) {
        usuario.setListaRoles(asociarEntidades.relacionar(usuario.getListaRoles(),repositoryFactory.generarRepositorio(NombreRepositorio.ROL.getRepoName())));
        usuario.setListaCalificacion(asociarEntidades.relacionar(usuario.getListaCalificacion(),repositoryFactory.generarRepositorio(NombreRepositorio.CALIFICACION.getRepoName())));
        usuario.setListaRespuesta(asociarEntidades.relacionar(usuario.getListaRespuesta(), repositoryFactory.generarRepositorio(NombreRepositorio.RESPUESTA.getRepoName())));
        usuario.setListaReaccion(asociarEntidades.relacionar(usuario.getListaReaccion(),repositoryFactory.generarRepositorio(NombreRepositorio.REACCION.getRepoName())));
        usuario.setListaComentarios(asociarEntidades.relacionar(usuario.getListaComentarios(),repositoryFactory.generarRepositorio(NombreRepositorio.COMENTARIO.getRepoName())));
        usuario.setListaUniversidad(asociarEntidades.relacionar(usuario.getListaUniversidad(), repositoryFactory.generarRepositorio(NombreRepositorio.UNIVERSIDAD.getRepoName())));
    }

}
