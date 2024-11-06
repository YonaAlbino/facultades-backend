package com.example.facultades.service;

import com.example.facultades.dto.BaseDTO;
import com.example.facultades.dto.ComentarioDTO;
import com.example.facultades.dto.DetalleNotificacion;
import com.example.facultades.dto.UniversidadDTO;
import com.example.facultades.enums.MensajeNotificacionAdmin;
import com.example.facultades.enums.NombreRepositorio;
import com.example.facultades.enums.Socket;
import com.example.facultades.excepciones.RegistroExistenteException;
import com.example.facultades.generics.GenericService;
import com.example.facultades.generics.IgenericService;
import com.example.facultades.model.Comentario;
import com.example.facultades.model.Notificacion;
import com.example.facultades.model.Universidad;
import com.example.facultades.repository.IUniversidadRepository;
import com.example.facultades.util.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    @Autowired
    private IgenericService<Comentario, Long> comentarioService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ComentarioService comenService;

    @Override
    public Universidad update(Universidad universidad) {
        if (Utili.verificarInsercionNuevoComentario(universidad, universidadRepository, universidad.getListaComentarios())) {
            Utili.enviarGuardarNotificacionNuevoComentario(universidad,universidad.getListaComentarios(), comentarioService, notificacionService);
        }
        this.asociar(universidad);
        return universidadRepository.save(universidad);
    }

    @Override
    public BaseDTO<Universidad> convertirDTO(Universidad universidad) {
        UniversidadDTO universidadDTO = modelMapper.map(universidad, UniversidadDTO.class);

        if(universidad.getListaComentarios() != null){
            List<ComentarioDTO> comentarioDTOS = new ArrayList<>();
            for (Comentario comentario : universidad.getListaComentarios()){
                comentarioDTOS.add((ComentarioDTO) comenService.convertirDTO(comentario));
            }
            universidadDTO.setListaComentarios(comentarioDTOS);
        }

        return universidadDTO;
    }

    @Override
    public Universidad converirEntidad(BaseDTO<Universidad> DTO) {
        Universidad universidad = modelMapper.map(DTO, Universidad.class);
        return universidad;
    }

    @Override
    public Universidad save(Universidad universidad){
        this.asociar(universidad);
        Universidad universidadGuardada = universidadRepository.save(universidad);
        if(universidadGuardada.getId() != null){
            Notificacion notificacion = new Notificacion();
            notificacion.setUniversidad(true);
            Utili.manejarNotificacionAdmin(MensajeNotificacionAdmin.CREACION_UNIVERSIDAD.getNotificacion(), universidadGuardada, notificacionService, notificacion);
            return universidadGuardada;
        }
        //manejar error en caso de no guarda la uni
        return universidadGuardada;
    }

    public boolean universidadExistente(String nombreUniversidad) throws RegistroExistenteException {
        String nombre = universidadRepository.buscarUniversidadPorNombre(nombreUniversidad);
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
        universidad.setListaComentarios(asociarEntidades.relacionar(universidad.getListaComentarios(), repositoryFactory.generarRepositorio(NombreRepositorio.COMENTARIO.getRepoName())));
        universidad.setListaCarreras(asociarEntidades.relacionar(universidad.getListaCarreras(), repositoryFactory.generarRepositorio(NombreRepositorio.CARRERA.getRepoName())));
        universidad.setListaCalificacion(asociarEntidades.relacionar(universidad.getListaCalificacion(), repositoryFactory.generarRepositorio(NombreRepositorio.CALIFICACION.getRepoName())));
    }

}
