package com.example.facultades.service;

import com.example.facultades.dto.BaseDTO;
import com.example.facultades.dto.CarreraDTO;
import com.example.facultades.dto.DetalleNotificacion;
import com.example.facultades.enums.MensajeNotificacionAdmin;
import com.example.facultades.enums.NombreRepositorio;
import com.example.facultades.enums.Socket;
import com.example.facultades.generics.BaseEntity;
import com.example.facultades.generics.GenericService;
import com.example.facultades.generics.IgenericService;
import com.example.facultades.model.Carrera;
import com.example.facultades.model.Comentario;
import com.example.facultades.model.Universidad;
import com.example.facultades.repository.ICarreraRepository;
import com.example.facultades.util.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Autowired
    private IgenericService<Comentario,Long> comentarioService;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Carrera save(Carrera carrera) {
        this.asociar(carrera);
        Carrera carreraGuardada =  carreraRepository.save(carrera);
        if(carreraGuardada.getId() != null){
            Utili.manejarNotificacionAdmin(MensajeNotificacionAdmin.CREACION_CARRERA.getNotificacion(), carreraGuardada, notificacionService);
            return carreraGuardada;
        }
        //Manejar error en caso no se guarde
        return carreraGuardada;
    }


    @Override
    public Carrera update(Carrera carrera) {
        if (Utili.verificarInsercionNuevoComentario(carrera, carreraRepository, carrera.getListaComentarios()))
            Utili.enviarGuardarNotificacionNuevoComentario(carrera.getListaComentarios(), comentarioService, notificacionService);
        else
            System.out.println("No tiene mas comentarios");
        this.asociar(carrera);
        return carreraRepository.save(carrera);
    }

    @Override
    public BaseDTO<Carrera> convertirDTO(Carrera carrera) {
        return modelMapper.map(carrera, CarreraDTO.class);
    }

    @Override
    public Carrera converirEntidad(BaseDTO<Carrera> DTO) {
        return modelMapper.map(DTO, Carrera.class);
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
