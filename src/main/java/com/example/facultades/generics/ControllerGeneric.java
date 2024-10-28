package com.example.facultades.generics;

import com.example.facultades.dto.BaseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

public class ControllerGeneric <E extends BaseEntity, D extends BaseDTO<E> ,ID extends Number>{

    @Autowired
    private IgenericService<E, ID> genericService;


    @GetMapping
    public ResponseEntity<List<D>> getAll(){
        List<E> entidades =  genericService.getAll();

        if (entidades == null || entidades.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<D> listaDTOS = new ArrayList<>();
        for (E entidad : entidades){
            listaDTOS.add((D) genericService.convertirDTO(entidad));
        }
        return ResponseEntity.ok(listaDTOS);
    }

    /*@GetMapping("/pepe/{id}")
    public ResponseEntity<E> findById(@PathVariable ID id){
        genericService.convertirDTO(genericService.findById(id).get());
        return ResponseEntity.ok(genericService.findById(id).get());
    }*/

    @GetMapping("/{id}")
    public ResponseEntity<BaseDTO<E>> findById(@PathVariable ID id){
        return  ResponseEntity.ok(genericService.convertirDTO(genericService.findById(id).get()));
        //return ResponseEntity.ok(genericService.findById(id).get());
    }

    @PutMapping
    public ResponseEntity<E> update(@RequestBody D dto){
        //rolService.procesarLista(rol);
        E entidad = genericService.converirEntidad(dto);
        System.out.println(entidad);
        E entidadActualizada = genericService.update(entidad);
        return ResponseEntity.ok(genericService.update(entidadActualizada));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable ID id){
         genericService.delete(id);
        String mensaje = "{\"mensaje\":\"" + "Eliminación exitosa" + "\"}";
        return ResponseEntity.ok(mensaje);
    }

    /*@PostMapping
    public ResponseEntity<E> save(@RequestBody E e){
        //rolService.procesarLista(rol);
        return ResponseEntity.ok(genericService.save(e));
    }*/

    @PostMapping
    public ResponseEntity<D> save(@RequestBody D dto){
        //rolService.procesarLista(rol);

        E entidad = genericService.converirEntidad(dto);
        System.out.println("controler " + entidad);
       E entidadGuardada = genericService.save(entidad);
       D dto1 = (D) genericService.convertirDTO(entidadGuardada);
       return  ResponseEntity.ok(dto1);
    }

}
