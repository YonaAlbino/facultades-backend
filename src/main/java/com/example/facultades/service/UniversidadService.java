package com.example.facultades.service;

import com.example.facultades.enums.MensajeNotificacionAdmin;
import com.example.facultades.enums.NombreRepositorio;
import com.example.facultades.enums.Socket;
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

    @Autowired
    private INotificacionService notificacionService;

    @Override
    public Universidad update(Universidad universidad) {
        this.asociar(universidad);
        return universidadRepository.save(universidad);
    }

    @Override
    public Universidad save(Universidad universidad){
        this.asociar(universidad);
        Universidad universidadGuardada = universidadRepository.save(universidad);
        if(universidadGuardada.getId() != null){
            notificacionService.enviarNotificacion(Socket.ADMIN_PREFIJO.getRuta(), MensajeNotificacionAdmin.CREACION_UNIVERSIDAD.getNotificacion());
            notificacionService.guardarNotificacionAdmin(universidadGuardada.getId(), MensajeNotificacionAdmin.CREACION_UNIVERSIDAD.getNotificacion());
            return universidadGuardada;
        }
        //manejar error en caso de no guarda la uni
        return universidadGuardada;
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
