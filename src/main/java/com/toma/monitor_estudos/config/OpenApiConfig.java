package com.toma.monitor_estudos.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Monitor de Estudos API")
                        .version("0.2.0")
                        .description("""
                                API REST para gerenciamento de matérias,
                                sessões de estudo e geração de estatísticas
                                de produtividade.
                        """)
                        .contact(new Contact()
                                .name("Thiago Toma")
                                .url("https://github.com/Thiago279")));
    }
}
