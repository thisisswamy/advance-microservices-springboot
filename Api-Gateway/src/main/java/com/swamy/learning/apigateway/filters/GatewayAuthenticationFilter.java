package com.swamy.learning.apigateway.filters;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@Component
public class GatewayAuthenticationFilter  extends AbstractGatewayFilterFactory<com.swamy.learning.apigateway.filters.GatewayAuthenticationFilter.Config>{
	
	
	@Autowired
	private RouteValidator routeValidator;
	public GatewayAuthenticationFilter() {
		super(Config.class);
	}

	@Override
	public GatewayFilter apply(Config config) {
		// TODO Auto-generated method stub
		return (exchange,chain)->{
			if(routeValidator.isSecured.test(exchange.getRequest())) {
				if(!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
					throw new RuntimeException("AUTHORIZATION headers missing");
				}
				String string = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
				System.err.println(string);
			}
			
			return chain.filter(exchange);
		};
	}
	
	static class Config{}

}
