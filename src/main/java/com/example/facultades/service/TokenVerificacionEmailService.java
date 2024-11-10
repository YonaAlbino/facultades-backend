package com.example.facultades.service;

import com.example.facultades.dto.BaseDTO;
import com.example.facultades.dto.CarreraDTO;
import com.example.facultades.dto.ComentarioDTO;
import com.example.facultades.dto.TokenVerificacionEmailDTO;
import com.example.facultades.generics.GenericService;
import com.example.facultades.generics.IGenericRepository;
import com.example.facultades.model.Carrera;
import com.example.facultades.model.Comentario;
import com.example.facultades.model.Rol;
import com.example.facultades.model.TokenVerificacionEmail;
import com.example.facultades.repository.ITokenVerificacionEmailRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class TokenVerificacionEmailService extends GenericService<TokenVerificacionEmail, Long> implements ITokenVerificacionEmailService{

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ITokenVerificacionEmailRepository tokenRepository;

    @Override
    public BaseDTO<TokenVerificacionEmail> convertirDTO(TokenVerificacionEmail tokenVerificacionEmail) {
        return modelMapper.map(tokenVerificacionEmail, TokenVerificacionEmailDTO.class);
    }

    @Override
    public TokenVerificacionEmail converirEntidad(BaseDTO<TokenVerificacionEmail> DTO) {
        return modelMapper.map(DTO, TokenVerificacionEmail.class);
    }


    @Override
    public TokenVerificacionEmail findByToken(String token) {
        return tokenRepository.findByToken(token);
    }
}
