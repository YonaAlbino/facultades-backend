package com.example.facultades.service;


import com.example.facultades.generics.GenericService;
import com.example.facultades.model.Universidad;
import com.example.facultades.model.UsuarioLeido;
import com.example.facultades.repository.IUsuarioLeidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioLeidoService extends GenericService<UsuarioLeido, Long> implements IUsuarioLeidoService{


}
