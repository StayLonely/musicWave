package ru.staylonely.course.musicservice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

@Configuration
public class MimeConfig implements WebMvcConfigurer {
    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.mediaType("mp3", MediaType.valueOf("audio/mpeg"));
    }

    @Value("${upload.directory}")
    private String uploadDir;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String absolutePath = new File(uploadDir).getAbsolutePath();

        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:" + absolutePath + "/")
                .setCachePeriod(0);
    }

}
