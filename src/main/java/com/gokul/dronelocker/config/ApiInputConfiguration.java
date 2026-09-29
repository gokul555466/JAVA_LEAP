package com.gokul.dronelocker.config;

import tools.jackson.databind.DeserializationFeature;
import org.springframework.boot.jackson.autoconfigure.JsonMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiInputConfiguration {

    @Bean
    public JsonMapperBuilderCustomizer strictIntegerInputCustomizer() {
        return builder -> builder.disable(DeserializationFeature.ACCEPT_FLOAT_AS_INT);
    }
}
