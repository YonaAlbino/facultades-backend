package com.example.facultades.appConfig;

import com.example.facultades.dto.ComentarioDTO;
import com.example.facultades.model.Comentario;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        // Configuración explícita para mapear el id de ComentarioDTO a Comentario
        modelMapper.typeMap(ComentarioDTO.class, Comentario.class).addMappings(mapper ->
                mapper.map(ComentarioDTO::getId, Comentario::setId)
        );

        return modelMapper;
    }
}
