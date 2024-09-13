package com.example.facultades.controller;


import com.example.facultades.generics.ControllerGeneric;
import com.example.facultades.model.Usuario;
import com.example.facultades.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuario")
//@PreAuthorize("hasRole('ADMIN')")
public class UsuarioController extends ControllerGeneric<Usuario,Long> {

    /*@Autowired
    private IUsuarioService usuarioService;

    @GetMapping
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Usuario>> getAll(){
        return ResponseEntity.ok(usuarioService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> findById(@PathVariable Long id){
        Optional<Usuario> usuario = usuarioService.findById(id);
        return usuario.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping
    public ResponseEntity<Usuario> update(@RequestBody Usuario usuario){
        usuarioService.procesarLista(usuario);
        usuario.setPassword(usuarioService.encriptPassword(usuario.getPassword()));
        return ResponseEntity.ok(usuarioService.update(usuario));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id){
        return  ResponseEntity.ok(usuarioService.delete(id));
    }

    @PostMapping
    public ResponseEntity<Usuario> save(@RequestBody Usuario usuario){
        usuarioService.procesarLista(usuario);
        usuario.setPassword(usuarioService.encriptPassword(usuario.getPassword()));
        return ResponseEntity.ok(usuarioService.save(usuario));
    }*/
}
