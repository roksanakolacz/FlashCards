package com.example.FlashCards;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.util.UrlPathHelper;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {


    @Bean
    public UrlPathHelper urlPathHelper() {
        UrlPathHelper urlPathHelper = new UrlPathHelper();
        urlPathHelper.setRemoveSemicolonContent(false); // Optional: If you want to keep path variables containing semicolons
        urlPathHelper.setRemoveSemicolonContent(true); // Set this to true to disable suffix pattern matching
        return urlPathHelper;
    }
    
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH");
    }
}