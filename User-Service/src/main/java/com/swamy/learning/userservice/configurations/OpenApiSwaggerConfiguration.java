package com.swamy.learning.userservice.configurations;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
		info = @Info(
				title = "User Microservice Swagger",
				description = "Swagger API's documentation",
				version = "1.0",
				contact = @Contact(
						email = "swamy.learning@gmail.com",
						name = "thisIs_swamy",
						url = "https://github.com/thisisswamy/"
						)
				), 
		
		servers = @Server(
				description = "Local ENV",
				url = "http://localhost:8081/api/v1/user/"
				),
		security = @SecurityRequirement(name = "bearerAuth")
		)
@SecurityScheme(
		name = "bearerAuth",
		description = "JWT auth description",
		scheme = "bearer",
		type = SecuritySchemeType.HTTP,
		bearerFormat = "JWT",
		in =SecuritySchemeIn.HEADER
		)
public class OpenApiSwaggerConfiguration {

}
