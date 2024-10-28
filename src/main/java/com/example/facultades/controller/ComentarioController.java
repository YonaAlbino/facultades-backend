package com.example.facultades.controller;

import com.example.facultades.dto.ComentarioDTO;
import com.example.facultades.dto.DetalleNotificacion;
import com.example.facultades.generics.ControllerGeneric;
import com.example.facultades.generics.IgenericService;
import com.example.facultades.model.Comentario;
import com.example.facultades.service.ComentarioService;
import com.example.facultades.service.IComentarioService;
import com.example.facultades.service.INotificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comentario")
public class ComentarioController extends ControllerGeneric<Comentario, ComentarioDTO,Long> {

    @Autowired
    private IComentarioService comentarioService;

    @Autowired
    private IgenericService<Comentario, Long> igenericService;

    @GetMapping("/entidad/{id}")
    public ResponseEntity<Comentario> entidad(@PathVariable Long id ){
        return  ResponseEntity.ok(igenericService.findById(id).get());
    }

    @Autowired
    private INotificacionService notificacionService;


    @GetMapping("/enviarNotificacionUsuario")
    public void enviarNotificacionUsuario(){
        DetalleNotificacion detalleNotificacion = new DetalleNotificacion("prueba","prueba", 1L);
        notificacionService.enviarNotificacionByWebSocket("/usuario/152",detalleNotificacion);
    }


    @GetMapping("/encontrarComentariosPorIdUniversidad/{idUniversidad}")
    public ResponseEntity<List<ComentarioDTO>> encontrarComentariosPorIdUniversidad(
            @PathVariable long idUniversidad,
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue =  "10") int tamanio){
        List<ComentarioDTO> listaComentarios = comentarioService.findComentariosByUniversidadId(idUniversidad, pagina, tamanio);
        return new ResponseEntity<>(listaComentarios, HttpStatus.OK);
    }

    @GetMapping("/encontrarComentariosPorIdCarrera/{idCarrera}")
    public ResponseEntity<List<Comentario>> encontrarComentariosPorIdCarrera(
            @PathVariable long idCarrera,
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue =  "10") int tamanio){
        List<Comentario> listaComentarios = comentarioService.findComentariosByCarreraId(idCarrera, pagina, tamanio);
        return new ResponseEntity<>(listaComentarios, HttpStatus.OK);
    }


}
