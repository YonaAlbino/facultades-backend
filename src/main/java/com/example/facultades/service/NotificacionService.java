package com.example.facultades.service;
import com.example.facultades.dto.BaseDTO;
import com.example.facultades.dto.DetalleNotificacion;
import com.example.facultades.dto.NotificacionDTO;
import com.example.facultades.enums.NombreRepositorio;
import com.example.facultades.generics.GenericService;
import com.example.facultades.model.Notificacion;
import com.example.facultades.model.Usuario;
import com.example.facultades.model.UsuarioLeido;
import com.example.facultades.repository.INotificacionRepository;
import com.example.facultades.repository.IUsuarioLeidoRepository;
import com.example.facultades.repository.IUsuarioRepository;
import com.example.facultades.util.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class NotificacionService extends GenericService<Notificacion, Long> implements INotificacionService, IEntidadAsociable<Notificacion> {

    @Autowired
    private INotificacionRepository notificacionRepo;

    @Autowired
    private IAsociarEntidades asociarEntidades;

    @Autowired
    private IRepositoryFactory repositoryFactory;

    @Autowired
    private IUsuarioRepository usuRepo;

    @Autowired
    private IUsuarioLeidoRepository usuarioLeidoRepository;

    @Autowired
    private ModelMapper modelMapper;


    public NotificacionService(SimpMessagingTemplate messagingTemplate){
        this.messagingTemplate = messagingTemplate;
    }

    private SimpMessagingTemplate messagingTemplate;

    @Override
    public Notificacion save(Notificacion notificacion) {
        this.asociar(notificacion);
        return notificacionRepo.save(notificacion);
    }

    @Override
    public Notificacion update(Notificacion notificacion) {
        return this.save(notificacion);
    }

    @Override
    public BaseDTO<Notificacion> convertirDTO(Notificacion notificacion) {
        return modelMapper.map(notificacion, NotificacionDTO.class);
    }

    @Override
    public Notificacion converirEntidad(BaseDTO<Notificacion> DTO) {
        return modelMapper.map(DTO, Notificacion.class);
    }


    @Override
    public void asociar(Notificacion notificacion) {
        notificacion.setListaUsuarios(asociarEntidades.relacionar(notificacion.getListaUsuarios(), repositoryFactory.generarRepositorio(NombreRepositorio.USUARIO.getRepoName())));
        notificacion.setListaDeusuariosLeidos(asociarEntidades.relacionar(notificacion.getListaDeusuariosLeidos(), repositoryFactory.generarRepositorio(NombreRepositorio.USUARIO_LEIDO.getRepoName())));
    }


    @Override
    public void enviarNotificacionByWebSocket(String topic,DetalleNotificacion detalleNotificacion) {
        ObjectMapper objectMapper = new ObjectMapper();
       // DetalleNotificacion detalleNotificacion = new DetalleNotificacion(evento, detalle, id);
        try {
            String detalleJson = objectMapper.writeValueAsString(detalleNotificacion);
            messagingTemplate.convertAndSend(topic, detalleJson);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void guardarNotificacionAdmin(Long idEvento, String informacion) {
        Notificacion notificacion = new Notificacion();
        List<UsuarioLeido> listaLeidos = new ArrayList<>();
        notificacion.setListaDeusuariosLeidos(listaLeidos);
        notificacion. setIdRedireccionamiento(idEvento);
        notificacion.setLeida(false);
        notificacion.setInformacion(informacion);
        notificacion.setListaUsuarios(usuRepo.findUserEntityByListaRolesNombreRol("ADMIN"));
        this.save(notificacion);
    }

    @Override
    public List<Notificacion> getNotificacionByIdUser(Long idUser) {
        return notificacionRepo.findNotificacionesByUsuarioId(idUser);
    }

    @Override
    public String eliminarUsuarioNotificacion(Long idNotificacion, Long idUsuario) {
        Notificacion notificacion = this.findById(idNotificacion).orElse(null);
        List<Usuario> nuevaListaUsu = new ArrayList<>();
        if(notificacion != null){
            for (Usuario usuario : notificacion.getListaUsuarios()){
                if(usuario.getId() != idUsuario){
                    nuevaListaUsu.add(usuario);
                }
            }
            notificacion.setListaUsuarios(nuevaListaUsu);
            this.update(notificacion);
            return "Notificación eliminada";
        }
        return "La notificación no existe";
    }

    @Override
    public List<Notificacion> findNotificacionesNoLeidasPorUsuario(Long userId) {
        List<Notificacion> listaNotificaciones = notificacionRepo.findNotificacionesByUsuarioId(userId);
        List<Notificacion> nuevaLista = new ArrayList<>();

        for (Notificacion notificacion : listaNotificaciones) {
            boolean leidaPorUsuario = false;

            if (notificacion.getListaDeusuariosLeidos().isEmpty()) {
                nuevaLista.add(notificacion);
            } else {
                for (UsuarioLeido usuarioLeido : notificacion.getListaDeusuariosLeidos()) {
                    if (userId.equals(usuarioLeido.getIdUsuario())) {
                        leidaPorUsuario = true;
                        break;
                    }
                }
                if (!leidaPorUsuario) {
                    nuevaLista.add(notificacion);
                }
            }
        }
        return nuevaLista;
    }

    @Override
    public String setNotificacionLeidaPorUsuario(Long userId) {
        List<Notificacion> listaNotificaciones = notificacionRepo.findNotificacionesByUsuarioId(userId);
        List<UsuarioLeido> listaUsuariosLeidos = new ArrayList<>();

        for (Notificacion notificacion : listaNotificaciones) {

            for (UsuarioLeido usuarioLeido : notificacion.getListaDeusuariosLeidos()){
                if (!usuarioLeido.getIdUsuario().equals(userId)) {
                    UsuarioLeido nuevoUsuarioLeido = new UsuarioLeido();
                    nuevoUsuarioLeido.setIdUsuario(userId);
                    listaUsuariosLeidos.add(nuevoUsuarioLeido);
                }
            }
            notificacion.setListaDeusuariosLeidos(listaUsuariosLeidos);
            this.update(notificacion);
        }
        return "Notificaciones vistas";
    }


    @Override
    public String visualizarNotificacionesByUserID(Long userId) {
        List<Notificacion> listaNotificaciones = notificacionRepo.findNotificacionesByUsuarioId(userId);

        for (Notificacion notificacion : listaNotificaciones){
            UsuarioLeido  usuarioLeido = new UsuarioLeido();
            usuarioLeido.setIdUsuario(userId);

            notificacion.getListaDeusuariosLeidos().add(usuarioLeidoRepository.save(usuarioLeido));
            this.update(notificacion);
        }
        return "Notificaciones visualizadas";
    }


    @Override
    public List<Notificacion> findByLeidaFalse(Long usuarioId) {
        return notificacionRepo.findByLeidaFalse(usuarioId);
    }

}
