package com.example.facultades.controller;

import com.example.facultades.generics.ControllerGeneric;
import com.example.facultades.model.Carrera;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carrera")
public class CarreraController extends ControllerGeneric<Carrera, Long> {

    /*@Autowired
    private ICarreraService carreraService;

    @Autowired
    private IComentarioService comentarioService;

    @GetMapping()
    public ResponseEntity<List<Carrera>> getCarrera(){
        List<Carrera> listaCarreras = carreraService.getAll();
        return new ResponseEntity<>(listaCarreras, HttpStatus.OK);
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<Carrera> findCarrera(@PathVariable Long id){
        Optional<Carrera> Carrera = carreraService.findById(id);
        if(Carrera.isPresent())
            return new ResponseEntity<>(Carrera.get(), HttpStatus.OK);
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @PostMapping()
    public ResponseEntity<Carrera> saveCarrera(@RequestBody Carrera carrera){
        carreraService.procesarLista(carrera);
        return ResponseEntity.ok(carreraService.save(carrera));
    }

    @PutMapping()
    public ResponseEntity<Carrera> editCarrera(@RequestBody Carrera carrera){
        carreraService.procesarLista(carrera);
        return new ResponseEntity<>(carreraService.update(carrera), HttpStatus.OK);
    }

    @DeleteMapping()
    public  ResponseEntity<String> deleteCarrera(@RequestParam Long id){
        String mensaje = carreraService.delete(id);
        return  new ResponseEntity<>(mensaje, HttpStatus.OK);
    }

    @GetMapping("/paginadas")
    public ResponseEntity<List<Carrera>>  obtenerCarrerasPaginadas(
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue =  "10") int tamanio)
    {
        Pageable pageable = PageRequest.of(pagina, tamanio);
        Page<Carrera> carreras = carreraService.obtenerCarrerasPaginadas(pageable);
        List<Carrera> listaCarreras = carreras.getContent();
        return new ResponseEntity<>(listaCarreras, HttpStatus.OK);
    }

    @GetMapping("/obtenerTopCarreras")
    public ResponseEntity<List<Carrera>> obtenerTopCarreras(
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue =  "10") int tamanio)
    {
        List<Carrera> carreras = carreraService.getTopCarreras(pagina, tamanio);
        return new ResponseEntity<>(carreras, HttpStatus.OK);
    }

     */

}
