package com.example.facultades.controller;

import com.example.facultades.excepciones.RegistroExistenteException;
import com.example.facultades.generics.ControllerGeneric;
import com.example.facultades.generics.GenericService;
import com.example.facultades.model.Universidad;
import com.example.facultades.service.IUniversidadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.sound.sampled.Control;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/universidad")
public class UniversidadController extends ControllerGeneric<Universidad, Long> {

    /*@Autowired
    private IUniversidadService universidadService;

    @GetMapping()
    public ResponseEntity<List<Universidad>> getUniversidades(){
        List<Universidad> listaUniversidades = universidadService.getAll();
        return new ResponseEntity<>(listaUniversidades, HttpStatus.OK);
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<Universidad> findUniversidad(@PathVariable Long id){
        Optional<Universidad> universidad = universidadService.findById(id);
        if (universidad.isPresent()) {
            return new ResponseEntity<>(universidad.get(), HttpStatus.OK);
        }
        return  new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @PostMapping()
    public ResponseEntity<Universidad> saveUniversidad(@RequestBody Universidad universidad) throws RegistroExistenteException {
        universidadService.procesarLista(universidad);
        return ResponseEntity.ok(universidadService.save(universidad));
    }

    @PutMapping()
    public ResponseEntity<Universidad> editUniversidad(@RequestBody Universidad universidad) throws  RegistroExistenteException {
        universidadService.procesarLista(universidad);
        return ResponseEntity.ok(universidadService.update(universidad));
    }

    @DeleteMapping()
    public ResponseEntity<String> eliminarUniversidad(@RequestParam Long id){
        String mensaje = universidadService.delete(id);
        return new ResponseEntity<>(mensaje, HttpStatus.OK);
    }

    @GetMapping("/obtenerTopUniversidades")
    public ResponseEntity<List<Universidad>> obtenerPrimerasTresImagenes(
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue =  "10") int tamanio)
    {
        List<Universidad> universidades = universidadService.obtenerTopUniversidades(pagina, tamanio);
        return new ResponseEntity<>(universidades, HttpStatus.OK);
    }

    @GetMapping("/paginadas")
    public ResponseEntity<List<Universidad>>  obtenerUniversidadesPaginadas(
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue =  "10") int tamanio)
    {
        Pageable pageable = PageRequest.of(pagina, tamanio);
        Page<Universidad> universidades = universidadService.obtenerUniversidadesPaginadas(pageable);
        List<Universidad> listaUniversidades = universidades.getContent();
        return new ResponseEntity<>(listaUniversidades, HttpStatus.OK);
    }

    @GetMapping("/universidadID/{idCarrera}")
    public ResponseEntity<Universidad>  getuniversidadIdCarrera(@PathVariable Long idCarrera){
           Universidad universidad = universidadService.getIDUniversidadPorCarreraId(idCarrera);
        return new ResponseEntity<>(universidad, HttpStatus.OK);
    }

    @GetMapping("/findUniversidadByName/{nombreUniversidad}")
    public ResponseEntity<List<Universidad>> findUniversidadByName(@PathVariable String nombreUniversidad) {
        List<Universidad> ListaUniversidades = universidadService.getUniversidadByName(nombreUniversidad);
        return new ResponseEntity<>(ListaUniversidades, HttpStatus.OK);
    }

     */

}
