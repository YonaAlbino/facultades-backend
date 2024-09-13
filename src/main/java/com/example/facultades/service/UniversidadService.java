package com.example.facultades.service;

import com.example.facultades.enums.NombreRepositorio;
import com.example.facultades.excepciones.RegistroExistenteException;
import com.example.facultades.generics.GenericService;
import com.example.facultades.model.Universidad;
import com.example.facultades.repository.IUniversidadRepository;
import com.example.facultades.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UniversidadService extends GenericService<Universidad, Long> implements IUniversidadService, IEntidadAsociable<Universidad> {

    @Autowired
    private IUniversidadRepository universidadRepository;

    @Autowired
    private IAsociarEntidades asociarEntidades;

    @Autowired
    private IRepositoryFactory repositoryFactory;


    @Override
    public Universidad update(Universidad universidad) {
        return this.save(universidad);
    }


    @Override
    public Universidad save(Universidad universidad){
        this.asociar(universidad);
        return universidadRepository.save(universidad);
    }

    public boolean universidadExistente(String nombreUniversidad) throws RegistroExistenteException {
        String nombre = universidadRepository.buscarUniversidadPorNombre(nombreUniversidad);
        System.out.println(nombre);
        if(nombre == null)
            return false;
        throw new RegistroExistenteException("La universidad que desas ingresar ya existe");
    }


    @Override
    public List<Universidad> obtenerTopUniversidades(int cantidadRegistros, int pagina) {
        Pageable pageable = PageRequest.of(cantidadRegistros, pagina);
        List<Universidad> universidades = universidadRepository.getTopUniversidades(pageable);
        return universidades;
    }

    @Override
    public Page<Universidad> obtenerUniversidadesPaginadas(Pageable pageable) {
        return universidadRepository.findAll(pageable);
    }

    @Override
    public Universidad getIDUniversidadPorCarreraId(Long carreraId) {
        return  universidadRepository.getIDUniversidadPorCarreraId(carreraId);
    }

    @Override
    public List<Universidad> getUniversidadByName(String nombreUniversidad) {
        List<Universidad> ListaUniversidades;
        ListaUniversidades = universidadRepository.getUniversidadByName(nombreUniversidad);
        return ListaUniversidades;
    }


    @Override
    public void asociar(Universidad universidad) {
        universidad.setListaCalificacion(asociarEntidades.relacionar(universidad.getListaCalificacion(), repositoryFactory.generarRepositorio(NombreRepositorio.CALIFICACION.getRepoName())));
        universidad.setListaCarreras(asociarEntidades.relacionar(universidad.getListaCarreras(), repositoryFactory.generarRepositorio(NombreRepositorio.CARRERA.getRepoName())));
        universidad.setListaCalificacion(asociarEntidades.relacionar(universidad.getListaCalificacion(), repositoryFactory.generarRepositorio(NombreRepositorio.CALIFICACION.getRepoName())));
    }

}
