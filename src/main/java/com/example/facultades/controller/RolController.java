package com.example.facultades.controller;


import com.example.facultades.generics.ControllerGeneric;
import com.example.facultades.model.Permiso;
import com.example.facultades.model.Rol;
import com.example.facultades.service.IPermisoService;
import com.example.facultades.service.IRolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/rol")
//@PreAuthorize("hasRole('ADMIN')")
public class RolController extends ControllerGeneric<Rol, Long> {

    /*
    @Autowired
    private IRolService rolService;


    //@Autowired
    //private IPermisoService permisoService;

    @GetMapping
    public ResponseEntity<List<Rol>> getAll(){
        return ResponseEntity.ok(rolService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Rol> findById(@PathVariable Long id){
        Optional<Rol> rol = rolService.findById(id);
        return rol.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping
    public ResponseEntity<Rol> update(@RequestBody Rol rol){
        rolService.procesarLista(rol);
        return ResponseEntity.ok(rolService.update(rol));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id){
        return ResponseEntity.ok(rolService.delete(id));
    }


    @Override
    @PostMapping
    public ResponseEntity<Rol> save(@RequestBody Rol rol){
        rolService.procesarLista(rol);
        System.out.println("pepe");
        return ResponseEntity.ok(rolService.save(rol));
    }

     */



}
