package com.example.facultades.controller;


import com.example.facultades.dto.UsuarioDTO;
import com.example.facultades.generics.ControllerGeneric;
import com.example.facultades.model.Usuario;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/usuario")
//@PreAuthorize("hasRole('ADMIN')")
public class UsuarioController extends ControllerGeneric<Usuario, UsuarioDTO, Long> {

}
