package com.swamy.learning.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class ApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayApplication.class, args);
	}
	
	@Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
            .route("auth-ms-service", r -> r
                .path("/api/v1/auth/**")
                .uri("lb://auth-ms-service"))
            
            .route("user-ms-service",r-> r.path("/api/v1/user/**").uri("lb://user-ms-service"))
            .route("notes-ms-service",r-> r.path("/api/v1/notes/**").uri("lb://notes-ms-service"))
            .build();
    }

}
