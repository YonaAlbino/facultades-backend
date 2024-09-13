package com.example.facultades.repository;

import com.example.facultades.generics.IGenericRepository;
import com.example.facultades.model.Reaccion;
import com.example.facultades.model.Respuesta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRespuestaRepository extends IGenericRepository<Respuesta, Long> {
}
