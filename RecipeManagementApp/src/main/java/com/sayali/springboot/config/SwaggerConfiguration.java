package com.sayali.springboot.config;

import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
//import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
@OpenAPIDefinition(security = { @SecurityRequirement(name = "bearer-key") })
public class SwaggerConfiguration {


	@Bean
	public OpenApiCustomiser recipeApi() {
		return openApi -> openApi.getComponents().addSecuritySchemes("bearer-key",
				new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT"));// type(SecuritySchemeType.HTTP).scheme("bearer").bearerFormat("JWT"));
	}

}
