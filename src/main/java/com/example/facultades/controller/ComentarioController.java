package com.example.facultades.controller;

import com.example.facultades.generics.ControllerGeneric;
import com.example.facultades.model.Comentario;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comentario")
public class ComentarioController extends ControllerGeneric<Comentario, Long> {

    /*@Autowired
    private IComentarioService comentarioService;

    @GetMapping()
    public ResponseEntity<List<Comentario>> getComentarios(@RequestBody Comentario comentario){
        List<Comentario> listaComentarios = comentarioService.getAll();
        return new ResponseEntity<>(listaComentarios, HttpStatus.OK);
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<Comentario> findComentario(@PathVariable long id){
        Optional<Comentario> comentario = comentarioService.findById(id);
        if(comentario.isPresent())
            return new ResponseEntity<>(comentario.get(), HttpStatus.OK);
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @PostMapping()
    public ResponseEntity<Comentario> saveComentario(@RequestBody Comentario comentario){
    comentarioService.procesarLista(comentario);
      return ResponseEntity.ok(comentarioService.save(comentario));
    }

    @PutMapping()
    public ResponseEntity<Comentario> editComentario(@RequestBody Comentario comentario){
        comentarioService.procesarLista(comentario);
        return ResponseEntity.ok(comentarioService.update(comentario));
    }

    @DeleteMapping()
    public ResponseEntity<String> deleteComentario(@PathVariable Long id){
       String mensaje = comentarioService.delete(id);
        return new ResponseEntity<>(mensaje, HttpStatus.OK);
    }

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

     */
}
