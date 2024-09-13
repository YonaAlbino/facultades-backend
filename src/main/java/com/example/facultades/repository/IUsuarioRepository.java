package com.example.facultades.repository;


import com.example.facultades.generics.IGenericRepository;
import com.example.facultades.model.Reaccion;
import com.example.facultades.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IUsuarioRepository extends IGenericRepository<Usuario, Long> {
    public Optional<Usuario> findUserEntityByusername(String username);
    public List<Usuario> findUserEntityByListaRolesNombreRol(String nombreRol);
}
