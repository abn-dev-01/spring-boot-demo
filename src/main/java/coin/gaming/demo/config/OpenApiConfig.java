package coin.gaming.demo.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
            .info(new Info().title("Spring Boot Demo project")
                            .description("Spring Boot demo application.")
                            .version("v0.0.1")
//                            .license(new License().name("Apache 2.0").url("http://springdoc.org"))
            )
            .externalDocs(new ExternalDocumentation()
                              .description("SpringShop Wiki Documentation")
                              .url("https://springshop.wiki.github.org/docs"));
    }
}
