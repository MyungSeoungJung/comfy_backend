package com.comfy_backend.configuration;

import com.comfy_backend.auth.AuthInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private AuthInterceptor authinterceptor;
    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods("*")
                 .allowedHeaders("*")
                 .allowCredentials(true);

    }
    @Override  //인터셉터 오버라이드 +등록이 되어 있어야 함+
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(authinterceptor);
    }
}
