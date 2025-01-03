package com.example.facultades.controller;

import com.example.facultades.dto.UniversidadDTO;
import com.example.facultades.excepciones.RegistroExistenteException;
import com.example.facultades.generics.ControllerGeneric;
import com.example.facultades.generics.GenericService;
import com.example.facultades.generics.IgenericService;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/universidad")
public class UniversidadController extends ControllerGeneric<Universidad, UniversidadDTO, Long> {

    @Autowired
    private IUniversidadService universidadService;

    @Autowired
    private IgenericService<Universidad, Long> IuniversidadGenericService;

    @GetMapping("/obtenerTopUniversidades")
    public ResponseEntity<List<Universidad>> obtenerPrimerasTresImagenes(
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue =  "10") int tamanio)
    {
        List<Universidad> universidades = universidadService.obtenerTopUniversidades(pagina, tamanio);
        return new ResponseEntity<>(universidades, HttpStatus.OK);
    }

    @GetMapping("/paginadas")
    public ResponseEntity<List<UniversidadDTO>>  obtenerUniversidadesPaginadas(
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue =  "10") int tamanio)
    {
        List<UniversidadDTO> listaUniversidadDTO = new ArrayList<>();
        Pageable pageable = PageRequest.of(pagina, tamanio);
        Page<Universidad> universidades = universidadService.obtenerUniversidadesPaginadas(pageable);
        List<Universidad> listaUniversidades = universidades.getContent();

        for (Universidad universidad:listaUniversidades){
            listaUniversidadDTO.add((UniversidadDTO) IuniversidadGenericService.convertirDTO(universidad));
        }
        return new ResponseEntity<>(listaUniversidadDTO, HttpStatus.OK);
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

    @GetMapping("/getAllComents/{id}")
    public  ResponseEntity<Integer> getAllComents(@PathVariable Long id){
         Universidad universidad = IuniversidadGenericService.findById(id).get();
         return ResponseEntity.ok( universidad.getListaComentarios().size());
    }

}
