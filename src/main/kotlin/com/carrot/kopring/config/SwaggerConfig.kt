package com.carrot.kopring.config


import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig {
    // SecuritySecheme명
    private val jwtSchemeName = "Bearer Authentication";

    val component: Components = Components().addSecuritySchemes(jwtSchemeName, createAPIKeyScheme())
    val securityRequirement: SecurityRequirement = SecurityRequirement().addList(jwtSchemeName)
    @Bean
    fun openAPI() : OpenAPI = OpenAPI().components(component).info(apiInfo()).addSecurityItem(securityRequirement)

    private fun apiInfo() = io.swagger.v3.oas.models.info.Info()
        .title("Travelit-Backend-API")
        .description("travelit backend swagger ui")
        .version("1.0.0")

    private fun createAPIKeyScheme() : SecurityScheme = SecurityScheme().type(SecurityScheme.Type.HTTP)
        .bearerFormat("JWT")
        .scheme("bearer")

}