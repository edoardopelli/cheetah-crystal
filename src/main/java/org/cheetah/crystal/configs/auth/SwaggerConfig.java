package org.cheetah.crystal.configs.auth;



import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme.In;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;

@Configuration
@SecurityScheme(
    name = "X-Crystal-Token",
    type = SecuritySchemeType.APIKEY,
    paramName = "X-Crystal-Token",
    in = SecuritySchemeIn.HEADER
)
public class SwaggerConfig {

    @Bean
     OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components().addSecuritySchemes("X-Crystal-Token",
                        new io.swagger.v3.oas.models.security.SecurityScheme()
                                .type(Type.APIKEY)
                                .in(In.HEADER)
                                .name("X-Crystal-Token")))
                .info(new Info().title("API Documentation").version("1.0"))
                .addSecurityItem(new io.swagger.v3.oas.models.security.SecurityRequirement().addList("X-Crystal-Token"));
    }
}