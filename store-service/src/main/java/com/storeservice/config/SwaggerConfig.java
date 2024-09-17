package com.storeservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI myOpenAPI() {
        Contact contact = new Contact();
        contact.setEmail("gabiabr3u@gmail.com");
        contact.setName("Gabriela Abreu");
        contact.setUrl("https://github.com/gabiaabreu");

        Info info = new Info()
                .title("Kafka Producer-Consumer API")
                .version("1.0")
                .contact(contact)
                .description("Practicing Kafka flow through an API");

        return new OpenAPI().info(info);
    }
}