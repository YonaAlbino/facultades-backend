package com.example.facultades.controller;

import com.example.facultades.dto.RespuestaDTO;
import com.example.facultades.generics.ControllerGeneric;
import com.example.facultades.generics.IGenericRepository;
import com.example.facultades.model.Respuesta;
import com.example.facultades.repository.IRespuestaRepository;
import com.example.facultades.service.IRespuestaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/respuesta")
public class RespuestaController extends ControllerGeneric<Respuesta, RespuestaDTO,Long> {

    @Autowired
   IRespuestaRepository respuestaRepository;

    @Autowired
    IGenericRepository<Respuesta, Long> iGenericRepository;

    /*@GetMapping("/getUsuarioByIdRespuesta/{idRespuesta}")
    public String getUsuarioByIdRespuesta(@PathVariable Long idRespuesta){
        Long idUsuario = respuestaRepository.findUsuarioIdByRespuestaId(idRespuesta);
        System.out.println(idUsuario);
        return iGenericRepository.findById(idUsuario).get().getUsuario().getUsername();
    }*/

}
