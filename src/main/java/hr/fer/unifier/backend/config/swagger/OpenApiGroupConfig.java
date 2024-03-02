package hr.fer.unifier.backend.config.swagger;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiGroupConfig {
    @Bean
    public GroupedOpenApi authOpenApi() {
        final String[] paths =  {"/auth/**"};
        return GroupedOpenApi
                .builder()
                .group("Auth")
                .pathsToMatch(paths)
                .build();
    }

    @Bean
    public GroupedOpenApi userOpenApi() {
        final String[] paths =  {"/user/**"};
        return GroupedOpenApi
                .builder()
                .group("User")
                .pathsToMatch(paths)
                .build();
    }

    @Bean
    public GroupedOpenApi requestOpenApi() {
        final String[] paths =  {"/request/**"};
        return GroupedOpenApi
                .builder()
                .group("Request")
                .pathsToMatch(paths)
                .build();
    }

    @Bean
    public GroupedOpenApi advertOpenApi() {
        final String[] paths =  {"/advert/**"};
        return GroupedOpenApi
                .builder()
                .group("Advert")
                .pathsToMatch(paths)
                .build();
    }

    @Bean
    public GroupedOpenApi dealOpenApi() {
        final String[] paths =  {"/deal/**"};
        return GroupedOpenApi
                .builder()
                .group("Deal")
                .pathsToMatch(paths)
                .build();
    }

    @Bean
    public GroupedOpenApi profileOpenApi() {
        final String[] paths =  {"/profile/**"};
        return GroupedOpenApi
                .builder()
                .group("Profile")
                .pathsToMatch(paths)
                .build();
    }

    @Bean
    public GroupedOpenApi recensionOpenApi() {
        final String[] paths =  {"/recension/**"};
        return GroupedOpenApi
                .builder()
                .group("Recension")
                .pathsToMatch(paths)
                .build();
    }

    @Bean
    public GroupedOpenApi allOpenApi() {
        final String[] paths =  {"/**"};
        return GroupedOpenApi
                .builder()
                .group("*")
                .pathsToMatch(paths)
                .build();
    }
}
