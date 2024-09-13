package com.example.facultades.repository;

import com.example.facultades.generics.IGenericRepository;
import com.example.facultades.model.Comentario;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface IComentarioRepository extends IGenericRepository<Comentario, Long> {
//
//    @Query("SELECT c " +
//            "FROM Comentario c " +
//            "WHERE c.id IN (" +
//            "    SELECT ulc.listaComentariosId " +
//            "    FROM Universidad.listaComentarios ulc " +
//            "    WHERE ulc.universidadId = :universidadId" +
//            ")")
//    List<Comentario> findComentariosByUniversidadId(@Param("universidadId") Long universidadId);


//    @Query("SELECT c " +
//            "FROM Comentario c " +
//            "WHERE c.id IN (" +
//            "    SELECT uc.id " +
//            "    FROM Universidad u " +
//            "    JOIN u.listaComentarios uc " +
//            "    WHERE u.id = :universidadId" +
//            ")")
//    List<Comentario> findComentariosByUniversidadId(@Param("universidadId") Long universidadId , Pageable pageable);


    @Query("SELECT c " +
            "FROM Comentario c " +
            "WHERE c.id IN (" +
            "    SELECT uc.id " +
            "    FROM Universidad u " +
            "    JOIN u.listaComentarios uc " +
            "    WHERE u.id = :universidadId" +
            ") " +
            "ORDER BY c.fecha DESC") // Ordenar por fecha descendente
    List<Comentario> findComentariosByUniversidadId(@Param("universidadId") Long universidadId , Pageable pageable);

    @Query("SELECT c " +
            "FROM Comentario c " +
            "WHERE c.id IN (" +
            "    SELECT uc.id " +
            "    FROM Carrera carrera " +
            "    JOIN carrera.listaComentarios uc " +
            "    WHERE carrera.id = :carrerraId" +
            ") " +
            "ORDER BY c.fecha DESC") // Ordenar por fecha descendente
    List<Comentario> findComentariosByCarreraId(@Param("carrerraId") Long carrerraId , Pageable pageable);






}
