package KDT.Web_IDE.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class SwaggerConfig {

  @Bean
  public OpenAPI webIDEAPI() {

    Info info = new Info().title("WEB IDE API").description("WEB IDE API 명세").version("0.0.1");

    String jwtSchemeName = "JWT";

    SecurityRequirement securityRequirement = new SecurityRequirement().addList(jwtSchemeName);

    Components components =
        new Components()
            .addSecuritySchemes(
                jwtSchemeName,
                new SecurityScheme()
                    .name(jwtSchemeName)
                    .type(SecurityScheme.Type.HTTP)
                    .scheme("Bearer")
                    .bearerFormat("JWT"));

    return new OpenAPI()
        .addServersItem(new Server().url("/"))
        .info(info)
        .addSecurityItem(securityRequirement)
        .components(components);
  }
}
