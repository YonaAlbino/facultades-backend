package com.example.facultades.controller;

import com.example.facultades.dto.MensajeRetornoSimple;
import com.example.facultades.dto.NotificacionDTO;
import com.example.facultades.excepciones.ComentarioNoEncontradoException;
import com.example.facultades.excepciones.RespuestaNoEncontradaException;
import com.example.facultades.generics.ControllerGeneric;
import com.example.facultades.generics.IgenericService;
import com.example.facultades.model.Comentario;
import com.example.facultades.model.Notificacion;
import com.example.facultades.model.Respuesta;
import com.example.facultades.repository.INotificacionRepository;
import com.example.facultades.service.INotificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/notificacion")
public class NotificacionController extends ControllerGeneric<Notificacion, NotificacionDTO,Long> {

    @Autowired
    private INotificacionService notificacionService;

    @Autowired
    private INotificacionRepository notificacionRepository;

    @Autowired
    private IgenericService<Comentario,Long> icomentarioServiceGeneric;

    @Autowired
    private IgenericService<Respuesta,Long> irespuestaServiceGeneric;

    @PutMapping("/leidas/{userId}")
    public ResponseEntity<String> actualizarNotificacionesALeidas(@PathVariable Long userId){
        notificacionRepository.marcarNotificacionesALeidas(userId);
        return ResponseEntity.ok("Notificaciones leidas");
    }

    @GetMapping("/false/{usuarioId}")
    public ResponseEntity<List<Notificacion>> getNotificationsFalse(@PathVariable Long usuarioId){
        return ResponseEntity.ok(notificacionService.findByLeidaFalse(usuarioId));
    }

    @GetMapping("/noLeidas/{usuarioId}")
    public ResponseEntity<List<NotificacionDTO>> getNotificationsNoLeidas(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(notificacionService.findNotificacionesNoLeidasPorUsuario(usuarioId));
    }

    @PostMapping("/setNotificacioneLeidasPorUsuario/{usuarioId}")
    public ResponseEntity<String> setNotificacioneLeidasPorUsuario(@PathVariable Long usuarioId) {
        String respuesta = notificacionService.setNotificacionLeidaPorUsuario(usuarioId);
        String mensaje = "{\"mensaje\":\"" + respuesta + "\"}";
        return ResponseEntity.ok(mensaje);
    }

    @PostMapping("/visualizarNotificacionesByUserID/{userId}")
    public ResponseEntity<String> visualizarNotificacionesByUserID(@PathVariable Long userId){
        String respuesta = notificacionService.visualizarNotificacionesByUserID(userId);
        String mensaje = "{\"mensaje\":\"" + respuesta + "\"}";
        return ResponseEntity.ok(mensaje);
    }

   /* @PostMapping("/notificarRespuestaRecibidaAcomentario/{idPropietarioComentario}/{idComentario}/{idRespuesta}")
    public ResponseEntity<MensajeRetornoSimple>  notificarRespuestaRecibidaAcomentario(@PathVariable Long idPropietarioComentario, @PathVariable Long idComentario, @PathVariable Long idRespuesta){
        String informacion = "";
        Optional<Comentario> comentarioOptional = icomentarioServiceGeneric.findById(idComentario);
        if(comentarioOptional.isPresent()){
            informacion = comentarioOptional.get().getMensaje();
        } else throw  new ComentarioNoEncontradoException();
        Notificacion notificacion = new Notificacion();
        notificacion.setFecha(new Date());
        notificacion.setRespuestaComentarioRecibida(true);
        notificacionService.guardarNotificacionUsuario(idPropietarioComentario,idRespuesta, "Has recibido una respuesta a tu comentario: "+ informacion, notificacion);
        return  ResponseEntity.ok(new MensajeRetornoSimple("Notificación enviada con exito"));
    }

    @PostMapping("/notificarRespuestaRecibidaAUnaRespuesta/{idPropietarioRespuesta}/{idRespuesta}/{idRespuestaRecibida}")
    public ResponseEntity<MensajeRetornoSimple>  notificarRespuestaRecibidaAUnaRespuesta(@PathVariable Long idPropietarioRespuesta, @PathVariable Long idRespuesta, @PathVariable Long idRespuestaRecibida){
        String informacion = "";
        Optional<Respuesta> respuestaDTO = irespuestaServiceGeneric.findById(idRespuesta);
        if(respuestaDTO.isPresent()){
            informacion = respuestaDTO.get().getMensaje();
        } else throw  new RespuestaNoEncontradaException();
        Notificacion notificacion = new Notificacion();
        notificacion.setFecha(new Date());
        notificacion.setRespuestaComentarioRecibida(true);
        notificacionService.guardarNotificacionUsuario(idPropietarioRespuesta,idRespuesta, "Has recibido una respuesta a tu comentario: "+ informacion, notificacion);
        return  ResponseEntity.ok(new MensajeRetornoSimple("Notificación enviada con exito"));
    }*/





    @PostMapping("/notificarRespuestaRecibidaAcomentario/{idPropietarioComentario}/{idComentario}/{idRespuesta}")
    public ResponseEntity<MensajeRetornoSimple> notificarRespuestaRecibidaAcomentario(@PathVariable Long idPropietarioComentario, @PathVariable Long idComentario, @PathVariable Long idRespuesta) {
        return notificarRespuestaRecibida(idPropietarioComentario, idComentario, idRespuesta, "comentario");
    }

    @PostMapping("/notificarRespuestaRecibidaAUnaRespuesta/{idPropietarioRespuesta}/{idRespuesta}/{idRespuestaRecibida}")
    public ResponseEntity<MensajeRetornoSimple> notificarRespuestaRecibidaAUnaRespuesta(@PathVariable Long idPropietarioRespuesta, @PathVariable Long idRespuesta, @PathVariable Long idRespuestaRecibida) {
        return notificarRespuestaRecibida(idPropietarioRespuesta, idRespuesta, idRespuestaRecibida, "respuesta");
    }

    private ResponseEntity<MensajeRetornoSimple> notificarRespuestaRecibida(Long propietarioId, Long entidadId, Long respuestaId, String tipoEntidad) {
        String informacion = obtenerInformacionEntidad(entidadId, tipoEntidad);

        Notificacion notificacion = new Notificacion();
        notificacion.setFecha(new Date());
        //notificacion.setRespuestaComentarioRecibida(true);

        if ("comentario".equals(tipoEntidad)) {
            notificacion.setRespuestaComentarioRecibida(true);
        } else {
            notificacion.setRespuestaAunaRespuesta(true);
        }


        notificacionService.guardarNotificacionUsuario(propietarioId, respuestaId, "Has recibido una respuesta a tu " + tipoEntidad + ": " + informacion, notificacion);

        return ResponseEntity.ok(new MensajeRetornoSimple("Notificación enviada con éxito"));
    }

    private String obtenerInformacionEntidad(Long id, String tipoEntidad) {
        Optional<?> entidad = "comentario".equals(tipoEntidad) ? icomentarioServiceGeneric.findById(id) : irespuestaServiceGeneric.findById(id);
        if (entidad.isPresent()) {
            return tipoEntidad.equals("comentario") ? ((Comentario) entidad.get()).getMensaje() : ((Respuesta) entidad.get()).getMensaje();
        } else {
            if ("comentario".equals(tipoEntidad)) {
                throw new ComentarioNoEncontradoException();
            } else {
                throw new RespuestaNoEncontradaException();
            }
        }
    }






    @GetMapping("/byUserId/{idUser}")
    public ResponseEntity<List<NotificacionDTO>> getNotificacionByIdUser(@PathVariable Long idUser){
        return ResponseEntity.ok(notificacionService.getNotificacionByIdUser(idUser));
    }

    @PutMapping("/{idNotificacion}/{idUsuario}")
    public ResponseEntity<String> eliminarUsuarioAsignado(@PathVariable Long idNotificacion, @PathVariable Long idUsuario){
        String respuesta = notificacionService.eliminarUsuarioNotificacion(idNotificacion, idUsuario);
        String mensaje = "{\"mensaje\":\"" + respuesta + "\"}";
       return ResponseEntity.ok(mensaje);
    }

}
