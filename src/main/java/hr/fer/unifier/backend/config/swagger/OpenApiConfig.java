package hr.fer.unifier.backend.config.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(info = @Info(
        contact = @Contact(
                name = "Iva Goluža",
                email = "iva.goluza@fer.hr"
        ),
        description = "Dokumentacija za projekt Unifier",
        title = "Unifier projekt",
        version = "1.0"
)
//        security = {
//                @SecurityRequirement(name = "bearerAuth")
//        }
        )
//@SecurityScheme(
//        name = "bearerAuth",
//        description = "JWT auth description",
//        scheme = "bearer",
//        type = SecuritySchemeType.HTTP,
//        bearerFormat = "JWT",
//        in = SecuritySchemeIn.HEADER
//)
public class OpenApiConfig {
}