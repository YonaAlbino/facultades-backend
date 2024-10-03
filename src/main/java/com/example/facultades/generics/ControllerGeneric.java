package com.example.facultades.generics;

import com.example.facultades.model.Rol;
import com.example.facultades.service.IPermisoService;
import com.example.facultades.service.IRolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

public class ControllerGeneric <E extends BaseEntity, ID extends Number>{

    @Autowired
    private IgenericService<E, ID> genericService;

    @GetMapping
    public ResponseEntity<List<E>> getAll(){
        return ResponseEntity.ok(genericService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<E> findById(@PathVariable ID id){
        return ResponseEntity.ok(genericService.findById(id).get());
    }

    @PutMapping
    public ResponseEntity<E> update(@RequestBody E e){
        //rolService.procesarLista(rol);
        return ResponseEntity.ok(genericService.update(e));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable ID id){
         genericService.delete(id);
        String mensaje = "{\"mensaje\":\"" + "Eliminación exitosa" + "\"}";
        return ResponseEntity.ok(mensaje);
    }

    @PostMapping
    public ResponseEntity<E> save(@RequestBody E e){
        //rolService.procesarLista(rol);
        return ResponseEntity.ok(genericService.save(e));
    }

}
