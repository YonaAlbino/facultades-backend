package com.example.facultades.service;

import com.example.facultades.enums.MensajeNotificacionAdmin;
import com.example.facultades.enums.NombreRepositorio;
import com.example.facultades.enums.Socket;
import com.example.facultades.generics.BaseEntity;
import com.example.facultades.generics.GenericService;
import com.example.facultades.generics.IGenericRepository;
import com.example.facultades.generics.IgenericService;
import com.example.facultades.model.Carrera;
import com.example.facultades.model.Comentario;
import com.example.facultades.repository.ICarreraRepository;
import com.example.facultades.util.IAsociarEntidades;
import com.example.facultades.util.IComentable;
import com.example.facultades.util.IEntidadAsociable;
import com.example.facultades.util.IRepositoryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarreraService extends GenericService<Carrera, Long> implements ICarreraService, IEntidadAsociable<Carrera> {

    @Autowired
    private ICarreraRepository carreraRepository;

    @Autowired
    private IAsociarEntidades asociarEntidades;

    @Autowired
    private IRepositoryFactory repositoryFactory;

    @Autowired
    private INotificacionService notificacionService;

    @Override
    public Carrera save(Carrera carrera) {
        this.asociar(carrera);
        Carrera carreraGuardada =  carreraRepository.save(carrera);
        if(carreraGuardada.getId() != null){
            notificacionService.enviarNotificacion(Socket.ADMIN_PREFIJO.getRuta(), MensajeNotificacionAdmin.CREACION_CARRERA.getNotificacion());
            notificacionService.guardarNotificacionAdmin(carreraGuardada.getId(), MensajeNotificacionAdmin.CREACION_CARRERA.getNotificacion());
            return carreraGuardada;
        }
        //Manejar error en caso no se guarde
        return carreraGuardada;
    }

    @Override
    public Carrera update(Carrera carrera) {
        if (verificarInsercionNuevoComentario(carrera, this, carrera.getComentarios()))
            System.out.println("tiene mas comentarios");
        else
            System.out.println("No tiene mas comentarios");
        this.asociar(carrera);
        return carreraRepository.save(carrera);
    }

    public <E extends BaseEntity & IComentable> Boolean verificarInsercionNuevoComentario(E entidad, IgenericService<E,Long> servicio, List<Comentario> listaComentarios){
        Optional<E> entidadGuardada =  servicio.findById(entidad.getId());
        //IComentable iComentable = (IComentable) entidadGuardada.get();
        List<Comentario> listaOriginal= entidadGuardada.get().getComentarios();

        if(listaComentarios.size() > listaOriginal.size())
            return true;
        else
            return false;
    }


    @Override
    public void asociar(Carrera carrera) {
        carrera.setListaComentarios(asociarEntidades.relacionar(carrera.getListaComentarios(), repositoryFactory.generarRepositorio(NombreRepositorio.COMENTARIO.getRepoName())));
        carrera.setListaCalificacion(asociarEntidades.relacionar(carrera.getListaCalificacion(), repositoryFactory.generarRepositorio(NombreRepositorio.CALIFICACION.getRepoName())));
    }

    @Override
    public Page<Carrera> obtenerCarrerasPaginadas(Pageable pageable) {
        return carreraRepository.findAll(pageable);
    }

    @Override
    public List<Carrera> getTopCarreras(int cantidadRegistros, int pagina) {
        Pageable pageable = PageRequest.of(cantidadRegistros, pagina);
        List<Carrera> carreras = carreraRepository.getTopCarreras(pageable);
        return carreras;
    }


}
