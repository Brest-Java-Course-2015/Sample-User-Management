package com.epam.brest.course2015.rest.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
@ComponentScan("com.epam.brest.course2015.*")
@PropertySource("classpath:/cors.properties")
public class WebConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private Environment env;

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        String[] origins = env.getProperty("allowed-origins").split(",");
        registry.addMapping("/rest/**")
                .allowedOrigins(origins)
                .allowedMethods("GET, POST, PUT, DELETE, OPTIONS")
                .allowedHeaders("Content-Type")
                .maxAge(3600);
    }

}
