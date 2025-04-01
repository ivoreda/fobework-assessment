package com.muzan.musicbookingapp.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.getConfiguration()
                .setSkipNullEnabled(true)
                .setAmbiguityIgnored(true)
                .setMatchingStrategy(MatchingStrategies.STRICT);

        return modelMapper;
    }
}