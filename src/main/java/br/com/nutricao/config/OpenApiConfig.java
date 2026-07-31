package br.com.nutricao.config;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.tags.Tag;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI apiNutricaoOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Nutricao")
                        .description("API REST para controle alimentar e nutricional")
                        .version("1.0"))
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .components(new Components()
                        .addSecuritySchemes("bearerAuth",
                                new SecurityScheme()
                                        .name("bearerAuth")
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                        .description("Informe o token JWT obtido no endpoint /api/v1/auth/login")))
                .addTagsItem(new Tag().name("Autenticacao").description("Login e obtencao de token JWT"))
                .addTagsItem(new Tag().name("Usuarios").description("Gerenciamento de usuarios"))
                .addTagsItem(new Tag().name("Categorias de Alimentos").description("Gerenciamento de categorias de alimentos"))
                .addTagsItem(new Tag().name("Alimentos").description("Gerenciamento de alimentos"))
                .addTagsItem(new Tag().name("Alimentos Favoritos").description("Gerenciamento de alimentos favoritos"))
                .addTagsItem(new Tag().name("Refeicoes").description("Gerenciamento de refeicoes"))
                .addTagsItem(new Tag().name("Alimentos da Refeicao").description("Gerenciamento de alimentos consumidos na refeicao"))
                .addTagsItem(new Tag().name("Metas Nutricionais").description("Gerenciamento de metas nutricionais"))
                .addTagsItem(new Tag().name("Objetivos").description("Gerenciamento de objetivos"))
                .addTagsItem(new Tag().name("Registros Diarios").description("Gerenciamento de registros diarios"));
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("api-nutricao")
                .pathsToMatch("/api/**")
                .build();
    }
}
