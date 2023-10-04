package com.swamy.learning.apigateway.filters;

import java.util.List;
import java.util.function.Predicate;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

@Component
public class RouteValidator {
	private List<String> notSecureURLS = List.of(
			"/api/v1/auth/token","/api/v1/auth/validateToken"
			);
	
	public Predicate<ServerHttpRequest> isSecured =
            request -> notSecureURLS
                    .stream()
                    .noneMatch(uri -> request.getURI().getPath().contains(uri));


}
