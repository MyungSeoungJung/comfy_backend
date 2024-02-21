package com.comfy_backend.auth.configuration;

import com.comfy_backend.auth.util.Hash;
import com.comfy_backend.auth.util.Jwt;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthBeanConfiguration {
    @Bean
    public Hash hash(){
        return new Hash();
    };
    @Bean
    public Jwt jwt(){
        return new Jwt();
    };
}
