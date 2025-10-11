package com.raicesvivas.backend.configs;

import com.raicesvivas.backend.models.dtos.UsuarioLoginDTO;
import com.raicesvivas.backend.models.entities.Usuario;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.addMappings(new PropertyMap<Usuario, UsuarioLoginDTO>() {
            @Override
            protected void configure() {
                map().setProvincia(source.getProvincia().getNombre());
            }
        });
        return mapper;
    }
}
