package com.example.facultades.service;

import com.example.facultades.enums.NombreRepositorio;
import com.example.facultades.generics.GenericService;
import com.example.facultades.model.Comentario;
import com.example.facultades.repository.IComentarioRepository;
import com.example.facultades.util.IAsociarEntidades;
import com.example.facultades.util.IEntidadAsociable;
import com.example.facultades.util.IRepositoryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComentarioService extends GenericService<Comentario, Long> implements IComentarioService, IEntidadAsociable<Comentario> {

    @Autowired
    private IComentarioRepository comentarioRepository;

    @Autowired
    private IAsociarEntidades asociarEntidades;

    @Autowired
    private IRepositoryFactory repositoryFactory;

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public Comentario save(Comentario comentario) {
        this.asociar(comentario);
        return comentarioRepository.save(comentario);
    }

    @Override
    public Comentario update(Comentario comentario) {
      return this.save(comentario);
    }

    @Override
    public void asociar(Comentario comentario) {
        comentario.setListaReaccion(asociarEntidades.relacionar(comentario.getListaReaccion(), repositoryFactory.generarRepositorio(NombreRepositorio.REACCION.getRepoName())));
        //comentario.setListaRespuesta(asociarEntidades.relacionar(comentario.getListaRespuesta(), repositoryFactory.generarRepositorio(NombreRepositorio.RESPUESTA.getRepoName())));
    }

    @Override
    public List<Comentario> findComentariosByUniversidadId(Long idUniversidad, int cantidadRegistros, int pagina) {
        Pageable pageable = PageRequest.of(cantidadRegistros, pagina);
        List<Comentario> listaComentarios = comentarioRepository.findComentariosByUniversidadId(idUniversidad, pageable);
        return listaComentarios;
    }

    @Override
    public List<Comentario> findComentariosByCarreraId(Long idCarrera, int cantidadRegistros, int pagina) {
        Pageable pageable = PageRequest.of(cantidadRegistros, pagina);
        List<Comentario> listaComentarios = comentarioRepository.findComentariosByCarreraId(idCarrera, pageable);
        return listaComentarios;
    }




}
