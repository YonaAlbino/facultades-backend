package com.example.facultades.controller;

import com.example.facultades.dto.NotificacionDTO;
import com.example.facultades.generics.ControllerGeneric;
import com.example.facultades.model.Notificacion;
import com.example.facultades.repository.INotificacionRepository;
import com.example.facultades.service.INotificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notificacion")
public class NotificacionController extends ControllerGeneric<Notificacion, NotificacionDTO,Long> {

    @Autowired
    private INotificacionService notificacionService;

    @Autowired
    private INotificacionRepository notificacionRepository;

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
    public ResponseEntity<List<Notificacion>> getNotificationsNoLeidas(@PathVariable Long usuarioId) {
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


    @GetMapping("/byUserId/{idUser}")
    public ResponseEntity<List<Notificacion>> getNotificacionByIdUser(@PathVariable Long idUser){
        return ResponseEntity.ok(notificacionService.getNotificacionByIdUser(idUser));
    }

    @PutMapping("/{idNotificacion}/{idUsuario}")
    public ResponseEntity<String> eliminarUsuarioAsignado(@PathVariable Long idNotificacion, @PathVariable Long idUsuario){
        String respuesta = notificacionService.eliminarUsuarioNotificacion(idNotificacion, idUsuario);
        String mensaje = "{\"mensaje\":\"" + respuesta + "\"}";
       return ResponseEntity.ok(mensaje);
    }

}
