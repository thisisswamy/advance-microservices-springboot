package com.swamy.learning.userservice.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
	
	private static final String PUBLIC_URLS[] = {
			"/register","/v2/api-docs","/v3/api-docs",
			"/v3/api-docs/**","/swagger-ui.html","swagger-ui/**",
			"/swagger-resources/**","/swagger-resources","/webjars/**",
			"/verify","/resend/verifyLink","/forgot-password",
			"/reset-password"
			
			
	
			};
	
	@Bean //To access to public unsecured urls
	public WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.ignoring().requestMatchers(PUBLIC_URLS);
	}
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.authorizeHttpRequests(authReq-> authReq.anyRequest().authenticated());
		return httpSecurity.build();
	}

}
