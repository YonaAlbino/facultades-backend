package com.example.facultades.service;
import com.example.facultades.enums.NombreRepositorio;
import com.example.facultades.generics.GenericService;
import com.example.facultades.model.Notificacion;
import com.example.facultades.repository.INotificacionRepository;
import com.example.facultades.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificacionService extends GenericService<Notificacion, Long> implements INotificacionService, IEntidadAsociable<Notificacion> {
    @Autowired
    private INotificacionRepository notificacionRepo;

    @Autowired
    private IAsociarEntidades asociarEntidades;

    @Autowired
    private IRepositoryFactory repositoryFactory;


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
    public void asociar(Notificacion notificacion) {
        notificacion.setListaUsuarios(asociarEntidades.relacionar(notificacion.getListaUsuarios(), repositoryFactory.generarRepositorio(NombreRepositorio.USUARIO.getRepoName())));
        notificacion.setListaUsuariosLeidos(asociarEntidades.relacionar(notificacion.getListaUsuariosLeidos(), repositoryFactory.generarRepositorio(NombreRepositorio.USUARIO_LEIDO.getRepoName())));
    }



    /*public NotificacionService(SimpMessagingTemplate messagingTemplate){
        this.messagingTemplate = messagingTemplate;
    }


    @Autowired
    private IUsuarioRepository usuRepo;

    @Autowired
    private IUsuarioLeidoService usuarioLeidoService;

    @Autowired
    @Lazy
    private IUsuarioService usuarioService;


    private SimpMessagingTemplate messagingTemplate;

    @Override
    public List<Notificacion> getAll() {
        return notificacionRepo.findAll();
    }

    @Override
    public Optional<Notificacion> findById(Long id) {
        return notificacionRepo.findById(id);
    }


    @Override
    public String delete(Long id) {
        notificacionRepo.deleteById(id);
        return "Notificacion eliminada";
    }

    @Override
    public void enviarNotificacion(String topic, String informacion) {

        String mensaje = "{\"mensaje\":\"" + informacion + "\"}";

        messagingTemplate.convertAndSend(topic, mensaje);
    }

    @Override
    public void guardarNotificacionAdmin(Long idEvento, String informacion) {
        Notificacion notificacion = new Notificacion();
        List<UsuarioLeido> listaLeidos = new ArrayList<>();
        notificacion.setUsuariosLeidos(listaLeidos);
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
                if(usuario.getIdUsuario() != idUsuario){
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

            if (notificacion.getUsuariosLeidos().isEmpty()) {
                nuevaLista.add(notificacion);
            } else {
                for (UsuarioLeido usuarioLeido : notificacion.getUsuariosLeidos()) {
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


    public String setNotificacionLeidaPorUsuario(Long userId) {
        List<Notificacion> listaNotificaciones = notificacionRepo.findNotificacionesByUsuarioId(userId);
        List<UsuarioLeido> listaUsuariosLeidos = new ArrayList<>();

        for (Notificacion notificacion : listaNotificaciones) {

            for (UsuarioLeido usuarioLeido : notificacion.getUsuariosLeidos()){
                if (!usuarioLeido.getIdUsuario().equals(userId)) {
                    UsuarioLeido nuevoUsuarioLeido = new UsuarioLeido();
                    nuevoUsuarioLeido.setIdUsuario(userId);
                    listaUsuariosLeidos.add(nuevoUsuarioLeido);
                }
            }
            notificacion.setUsuariosLeidos(listaUsuariosLeidos);
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

            notificacion.getUsuariosLeidos().add(usuarioLeidoService.save(usuarioLeido));
            this.update(notificacion);
        }
        return "Notificaciones visualizadas";
    }

    @Override
    public void procersarLista(Notificacion notificacion) {
        notificacion.setListaUsuarios(ProcesarLista.procesarLista(notificacion.getListaUsuarios(), usuarioService));
        notificacion.setUsuariosLeidos(ProcesarLista.procesarLista(notificacion.getUsuariosLeidos(), usuarioLeidoService));
    }

    @Override
    public List<Notificacion> findByLeidaFalse(Long usuarioId) {
        return notificacionRepo.findByLeidaFalse(usuarioId);
    }

     */
}
