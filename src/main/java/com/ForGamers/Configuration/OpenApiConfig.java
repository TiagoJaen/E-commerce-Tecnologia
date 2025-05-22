package com.ForGamers.Configuration;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("🚀 ForGamers E-Commerce API") // Título principal
                        .version("1.0.0")                    // Versión
                        .description("Documentación oficial de la API de ForGamers E-Commerce")); // Descripción
    }
}