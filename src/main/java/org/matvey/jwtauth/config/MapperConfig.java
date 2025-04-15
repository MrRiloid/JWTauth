package org.matvey.jwtauth.config;

import org.matvey.jwtauth.dto.UserDto;
import org.matvey.jwtauth.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class MapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}