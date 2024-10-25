package com.example.facultades.service;

import com.example.facultades.dto.BaseDTO;
import com.example.facultades.dto.RespuestaDTO;
import com.example.facultades.enums.NombreRepositorio;
import com.example.facultades.generics.GenericService;
import com.example.facultades.model.Respuesta;
import com.example.facultades.repository.IRespuestaRepository;
import com.example.facultades.util.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RespuestaService extends GenericService<Respuesta, Long> implements IRespuestaService, IEntidadAsociable<Respuesta> {

    @Autowired
    private IAsociarEntidades asociarEntidades;

    @Autowired
    private IRespuestaRepository respuestaRepository;

    @Autowired
    private ModelMapper modelMapper;


    @Autowired
    private IRepositoryFactory repositoryFactory;

    @Override
    public Respuesta update(Respuesta respuesta) {
      return this.save(respuesta);
    }

    @Override
    public BaseDTO<Respuesta> convertirDTO(Respuesta respuesta) {
        return modelMapper.map(respuesta, RespuestaDTO.class);
    }

    @Override
    public Respuesta converirEntidad(BaseDTO<Respuesta> DTO) {
        return modelMapper.map(DTO, Respuesta.class);
    }

    @Override
    public Respuesta save(Respuesta respuesta) {
        this.asociar(respuesta);
        return respuestaRepository.save(respuesta);
    }

    @Override
    public void asociar(Respuesta respuesta) {
        //respuesta.setListaRespuesta(asociarEntidades.relacionar(respuesta.getListaRespuesta(), repositoryFactory.generarRepositorio(NombreRepositorio.RESPUESTA.getRepoName())));
        //respuesta.setListaReaccion(asociarEntidades.relacionar(respuesta.getListaReaccion(), repositoryFactory.generarRepositorio(NombreRepositorio.REACCION.getRepoName())));
    }

}
