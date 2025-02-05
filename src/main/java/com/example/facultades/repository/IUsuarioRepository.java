package com.example.facultades.repository;


import com.example.facultades.generics.IGenericRepository;
import com.example.facultades.model.Comentario;
import com.example.facultades.model.Reaccion;
import com.example.facultades.model.Usuario;
import com.example.facultades.util.IFindComenByEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IUsuarioRepository extends IGenericRepository<Usuario, Long> {
    public Optional<Usuario> findUserEntityByusername(String username);
    public List<Usuario> findUserEntityByListaRolesNombreRol(String nombreRol);
    @Query("SELECT u FROM Usuario u WHERE u.username = :username")
    Usuario buscarUsuarioPorNombre(@Param("username")String username);

    @Query("SELECT u.imagen FROM Usuario u WHERE u.id = :id")
    String buscarImagenPorIdUser(@Param("id")Long id);

    @Query(value = "SELECT u.username FROM usuario u " +
            "INNER JOIN usuario_lista_universidad ul ON u.id = ul.usuario_id " +
            "INNER JOIN universidad un ON ul.lista_universidad_id = un.id " +
            "WHERE un.id = :universidadId " +
            "LIMIT 1", nativeQuery = true)
    String findFirstUsernameByUniversidadIdNative(@Param("universidadId") Long universidadId);



    @Query("SELECT u FROM Usuario u WHERE u.nick = :nick")
    Usuario buscarUsuarioPorNick(@Param("nick")String nick);
}
