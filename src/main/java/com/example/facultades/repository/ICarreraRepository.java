package com.example.facultades.repository;

import com.example.facultades.generics.IGenericRepository;
import com.example.facultades.model.Carrera;
import com.example.facultades.model.Comentario;
import com.example.facultades.util.IFindComenByEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ICarreraRepository extends IGenericRepository<Carrera, Long>, IFindComenByEntity {

    //@Modifying
   // @Query("DELETE FROM UniversidadListaCarreras u WHERE u.listaCarrerasId = :id")
    //void eliminarAsociacionUniversidadCarrera(@Param("id") Long id);

    @Query("SELECT carrera, AVG(c.nota) AS promedio " +
            "FROM Carrera carrera " +
            "JOIN carrera.listaCalificacion c " +
            "GROUP BY carrera " +
            "ORDER BY promedio DESC")
    List<Carrera> getTopCarreras(Pageable pageable);

    @Override
    @Query("SELECT c " +
            "FROM Comentario c " +
            "WHERE c.id IN (" +
            "    SELECT uc.id " +
            "    FROM Carrera carrera " +
            "    JOIN carrera.listaComentarios uc " +
            "    WHERE carrera.id = :carrerraId" +
            ") " +
            "ORDER BY c.fecha DESC")
    List<Comentario> getAllComenByEntity(Long carrerraId);

}
