package com.example.facultades.service;

import com.example.facultades.generics.GenericService;
import com.example.facultades.model.Reaccion;
import com.example.facultades.repository.IReaccionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReaccionService extends GenericService<Reaccion, Long> implements IReaccionService {

}
