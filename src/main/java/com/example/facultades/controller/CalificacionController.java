package com.example.facultades.controller;

import com.example.facultades.dto.CalificacionDTO;
import com.example.facultades.generics.ControllerGeneric;
import com.example.facultades.model.Calificacion;
import com.example.facultades.service.ICalificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/calificacion")
public class CalificacionController extends ControllerGeneric<Calificacion, CalificacionDTO,Long> {

}
