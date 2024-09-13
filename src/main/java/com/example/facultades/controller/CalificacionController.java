package com.example.facultades.controller;

import com.example.facultades.generics.ControllerGeneric;
import com.example.facultades.model.Calificacion;
import com.example.facultades.service.ICalificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/calificacion")
public class CalificacionController extends ControllerGeneric<Calificacion, Long> {

   /* @Autowired
    private ICalificacionService calificacionService;

    @GetMapping()
    public ResponseEntity<List<Calificacion>> getCalificaciones(){
        List<Calificacion> listaCalificaciones = calificacionService.getAll();
        return new ResponseEntity<>(listaCalificaciones, HttpStatus.OK);
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<Calificacion> findCalificacion(@PathVariable Long id){
        Optional<Calificacion> calificacion = calificacionService.findById(id);
        if(calificacion.isPresent())
            return  new ResponseEntity<>(calificacion.get(), HttpStatus.OK);
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @PostMapping()
    public ResponseEntity<Calificacion> saveCalificacion(@RequestBody Calificacion calificacion){
       calificacionService.save(calificacion);
        System.out.println(calificacion.getIdCalificacion());
        return new ResponseEntity<>(calificacion, HttpStatus.OK);
    }

    @DeleteMapping()
    public ResponseEntity<String> deleteCalificacion(@RequestParam Long id){
      String mensaje = calificacionService.delete(id);
      return  new ResponseEntity<>(mensaje, HttpStatus.OK);
    }

    @PutMapping()
    public ResponseEntity<Calificacion> editCalificacion(@RequestBody Calificacion calificacion){
        Calificacion calificacion1 = calificacionService.update(calificacion);
        return  new ResponseEntity<>(calificacion1, HttpStatus.OK);
    }*/
}
