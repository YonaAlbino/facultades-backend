package com.example.facultades.controller;

import com.example.facultades.dto.BaseDTO;
import com.example.facultades.dto.CarreraDTO;
import com.example.facultades.generics.ControllerGeneric;
import com.example.facultades.model.Carrera;
import com.example.facultades.service.ICarreraService;
import com.example.facultades.service.IComentarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carrera")
public class CarreraController extends ControllerGeneric<Carrera,CarreraDTO,Long> {

    @Autowired
    private ICarreraService carreraService;

    @Autowired
    private IComentarioService comentarioService;



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


}
