package com.example.facultades.controller;

import com.example.facultades.generics.ControllerGeneric;
import com.example.facultades.model.Comentario;
import com.example.facultades.service.IComentarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comentario")
public class ComentarioController extends ControllerGeneric<Comentario, Long> {

    @Autowired
    private IComentarioService comentarioService;

    @GetMapping("/encontrarComentariosPorIdUniversidad/{idUniversidad}")
    public ResponseEntity<List<Comentario>> encontrarComentariosPorIdUniversidad(
            @PathVariable long idUniversidad,
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue =  "10") int tamanio){
        List<Comentario> listaComentarios = comentarioService.findComentariosByUniversidadId(idUniversidad, pagina, tamanio);
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
