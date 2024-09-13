package com.example.facultades.controller;

import com.example.facultades.generics.ControllerGeneric;
import com.example.facultades.model.Respuesta;
import com.example.facultades.service.IRespuestaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/respuesta")
public class RespuestaController extends ControllerGeneric<Respuesta, Long> {

    /*@Autowired
    private IRespuestaService respuestaService;


    @GetMapping()
    public ResponseEntity<List<Respuesta>> getRespuestas(){
        List<Respuesta> listaRespuestas = respuestaService.getAll();
        return new  ResponseEntity<>(listaRespuestas, HttpStatus.OK);
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<Respuesta> findRespuesta(@PathVariable Long id){
        Optional<Respuesta> respuesta = respuestaService.findById(id);
        if(respuesta.isPresent())
            return new ResponseEntity<>(respuesta.get(), HttpStatus.OK);
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @PutMapping()
    public ResponseEntity<Respuesta> editRespuesta(@RequestBody Respuesta respuesta){
        respuestaService.procesarLista(respuesta);
        return new ResponseEntity<>(respuestaService.update(respuesta), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<Respuesta> saveRespuesta(@RequestBody Respuesta respuesta){
        respuestaService.procesarLista(respuesta);
        return new ResponseEntity<>(respuestaService.save(respuesta), HttpStatus.OK);
    }

    @DeleteMapping()
    public ResponseEntity<String> deleteRespuesta(@PathVariable Long id){
        String mensaje = respuestaService.delete(id);
        return new ResponseEntity<>(mensaje, HttpStatus.OK);
    }

     */
}
