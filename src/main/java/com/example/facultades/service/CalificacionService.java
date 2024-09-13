package com.example.facultades.service;

import com.example.facultades.generics.GenericService;
import com.example.facultades.generics.IgenericService;
import com.example.facultades.model.Calificacion;
import com.example.facultades.repository.ICalificacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class CalificacionService extends GenericService<Calificacion, Long> implements ICalificacionService{

   /* @Autowired
    private ICalificacionRepository calificacionRepository;

    @Override
    public List<Calificacion> getAll() {
        List<Calificacion> listaCalificaciones = calificacionRepository.findAll();
        return listaCalificaciones;
    }

    @Override
    public Calificacion save(Calificacion calificacion) {
        calificacionRepository.save(calificacion);
        return calificacion;
    }

    @Override
    public String delete(Long id) {
        calificacionRepository.deleteById(id);
        return "Calificación elimanada con exito";
    }

    @Override
    public Optional<Calificacion> findById(Long id) {
        Optional<Calificacion> calificationOptional = calificacionRepository.findById(id);
        return calificationOptional;
    }

    @Override
    public Calificacion update(Calificacion calificacion) {
        this.save(calificacion);
        return calificacion;
    }*/

}
