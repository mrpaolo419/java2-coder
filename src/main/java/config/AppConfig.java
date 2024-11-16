package config;



import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public OpenAPI tiendaFalsaOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("API de Tienda Falsa")
                        .description("API para gestionar productos, categorías y otras funcionalidades en una tienda ficticia.")
                        .version("v1.0.0")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")))
                .externalDocs(new ExternalDocumentation()
                        .description("Documentación adicional de la API de Tienda Falsa")
                        .url("https://faketienda.docs.github.io"));
    }
}

