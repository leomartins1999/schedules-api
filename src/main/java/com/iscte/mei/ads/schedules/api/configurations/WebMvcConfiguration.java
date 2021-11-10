package com.iscte.mei.ads.schedules.api.configurations;

import com.iscte.mei.ads.schedules.api.properties.CorsProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
@EnableWebMvc
public class WebMvcConfiguration extends WebMvcConfigurationSupport {

    private final CorsProperties properties;

    public WebMvcConfiguration(CorsProperties properties) {
        this.properties = properties;
    }

    @Override
    protected void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(properties.getAllowedOrigin());
    }
}
