package com.example.facultades.service;

import com.example.facultades.dto.BaseDTO;
import com.example.facultades.dto.ComentarioDTO;
import com.example.facultades.dto.RespuestaDTO;
import com.example.facultades.enums.NombreRepositorio;
import com.example.facultades.excepciones.ComentarioNoEncontradoException;
import com.example.facultades.generics.GenericService;
import com.example.facultades.model.Comentario;
import com.example.facultades.model.Respuesta;
import com.example.facultades.repository.IComentarioRepository;
import com.example.facultades.util.IAsociarEntidades;
import com.example.facultades.util.IEntidadAsociable;
import com.example.facultades.util.IRepositoryFactory;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ComentarioService extends GenericService<Comentario, Long> implements IComentarioService, IEntidadAsociable<Comentario> {

    @Autowired
    private IComentarioRepository comentarioRepository;

    @Autowired
    private IAsociarEntidades asociarEntidades;


    @Autowired
    private IRepositoryFactory repositoryFactory;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private  RespuestaService respuestaService;

   // @PreAuthorize("hasRole('ADMIN')")
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
    public BaseDTO<Comentario> convertirDTO(Comentario comentario) {
        ComentarioDTO comentarioDTO = modelMapper.map(comentario, ComentarioDTO.class);

        if (comentario.getUsuario() != null && comentario.getUsuario().getUsername() != null) {
            comentarioDTO.setUsername(comentario.getUsuario().getUsername());
        }
        // Convertir la lista de respuestas del comentario usando el servicio de Respuesta
        if(comentario.getListaRespuesta() != null){
            List<RespuestaDTO> listaRespuestasDTO = new ArrayList<>();
            for (Respuesta respuesta : comentario.getListaRespuesta()) {
                RespuestaDTO respuestaDTO = (RespuestaDTO) respuestaService.convertirDTO(respuesta);
                listaRespuestasDTO.add(respuestaDTO);
            }
            comentarioDTO.setListaRespuesta(listaRespuestasDTO);
        }
        return comentarioDTO;
    }


    @Override
    public Comentario converirEntidad(BaseDTO<Comentario> DTO) {
        Comentario comentario = modelMapper.map(DTO, Comentario.class);
        return comentario;
    }



    @Override
    public void asociar(Comentario comentario) {
        comentario.setListaReaccion(asociarEntidades.relacionar(comentario.getListaReaccion(), repositoryFactory.generarRepositorio(NombreRepositorio.REACCION.getRepoName())));
        //comentario.setListaRespuesta(asociarEntidades.relacionar(comentario.getListaRespuesta(), repositoryFactory.generarRepositorio(NombreRepositorio.RESPUESTA.getRepoName())));
    }

    @Override
    public List<ComentarioDTO> findComentariosByUniversidadId (Long idUniversidad, int cantidadRegistros, int pagina, boolean recientes, boolean antiguos, boolean votados ) {
        Pageable pageable = PageRequest.of(cantidadRegistros, pagina);
        List<Comentario> listaComentarios = new ArrayList<>();
        if(recientes){
            listaComentarios = comentarioRepository.findComentariosByUniversidadId(idUniversidad, pageable);
        } else if (antiguos) {
            listaComentarios = comentarioRepository.findComentariosByUniversidadIdAsc(idUniversidad, pageable);

        }else if (votados){
            listaComentarios = comentarioRepository.buscarComentariosOrdenadosMeGusta(idUniversidad, pageable);
        }
        List<ComentarioDTO> listaComentarioDTO = new ArrayList<>();
        for (Comentario comentario : listaComentarios){
            listaComentarioDTO.add((ComentarioDTO) this.convertirDTO(comentario));
        }
        return listaComentarioDTO;
    }

    @Override
    public List<ComentarioDTO> findComentariosByCarreraId (Long idCarrera, int cantidadRegistros, int pagina,boolean recientes,boolean antiguos,boolean votados) {
        Pageable pageable = PageRequest.of(cantidadRegistros, pagina);
        List<Comentario> listaComentarios = new ArrayList<>();
        if(recientes){
            listaComentarios = comentarioRepository.findComentariosByCarreraId(idCarrera, pageable);
        } else if(antiguos){
            listaComentarios = comentarioRepository.findComentariosByCarreraIdAsc(idCarrera, pageable);
        }

        List<ComentarioDTO> listaComentarioDTO = new ArrayList<>();
        for (Comentario comentario : listaComentarios){
            listaComentarioDTO.add((ComentarioDTO) this.convertirDTO(comentario));
        }
        return listaComentarioDTO;
    }

   /* @Override
    public ComentarioDTO findComentariosByRespuestaRespuestaId(Long idRespuestaRespuesta) {
        Optional<Comentario> comentarioOptional =  comentarioRepository.findComentariosByRespuestaRespuestaId(idRespuestaRespuesta);
        if(comentarioOptional.isEmpty())
            throw  new ComentarioNoEncontradoException();
        else return (ComentarioDTO) this.convertirDTO(comentarioOptional.get());
    }*/

}
