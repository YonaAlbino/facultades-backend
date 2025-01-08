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
    @Query("SELECT u.username FROM Usuario u WHERE u.username = :username")
    String buscarUsuarioPorNombre(@Param("username")String username);

    @Query("SELECT u.imagen FROM Usuario u WHERE u.id = :id")
    String buscarImagenPorIdUser(@Param("id")Long id);

    /*@Query("SELECT u.username FROM Usuario u " +
            "JOIN u.listaUniversidad ul " + // Aquí unimos Usuario con la entidad intermedia
            "JOIN ul.universidad univ " + // Aquí unimos con la entidad Universidad
            "WHERE univ.id = :universidadId")
    List<String> findUsernamesByUniversidadId(Long universidadId);*/



}
