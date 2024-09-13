package com.example.facultades.service;


import com.example.facultades.generics.GenericService;
import com.example.facultades.model.Permiso;
import com.example.facultades.repository.IPermisoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PermisoService extends GenericService<Permiso, Long> implements IPermisoService {

    /*@Autowired
    private IPermisoRepository permisoRepo;

    @Override
    public List<Permiso> getAll() {
        return permisoRepo.findAll();
    }

    @Override
    public Optional<Permiso> findById(Long id) {
        return permisoRepo.findById(id);
    }

    @Override
    public Permiso save(Permiso permiso) {
        return permisoRepo.save(permiso);
    }

    @Override
    public Permiso update(Permiso permiso) {
        return this.save(permiso);
    }

    @Override
    public void delete(Long id) {
        permisoRepo.deleteById(id);
    }*/

}
