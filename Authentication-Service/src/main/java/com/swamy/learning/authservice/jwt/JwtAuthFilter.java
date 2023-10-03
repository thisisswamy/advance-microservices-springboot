package com.swamy.learning.authservice.jwt;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.hibernate.annotations.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.swamy.learning.authservice.entity.ApplicationUser;
import com.swamy.learning.authservice.exceptions.GenericAuthException;
import com.swamy.learning.authservice.repo.ApplicationUserRepo;
import com.swamy.learning.authservice.services.CustomUserDetailsService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Filter(name = "jwt_auth_filter")
@Component
public class JwtAuthFilter extends OncePerRequestFilter {
	
	@Autowired
	private JwtAuthService authService;
	private ObjectMapper objectMapper = new ObjectMapper();
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		//Extractiong Authorization Header
		String header = request.getHeader("Authorization");
		
		//Setting default values to null
		String userEmail = null;
		String jwtToken=  null;
		Map<String ,String> errorResponse = new HashMap<>();
		Calendar calendar = Calendar.getInstance();
		
		//checking incoming http request has Bearer AUthorization token or not
		if(header !=null && header.startsWith("Bearer ")) {
			//Extracting token part alone
			jwtToken = header.substring(7);
			//extracting username from jwt Token -> return error ,message if token invalid/expaired..
			try {
				userEmail = authService.extractUsername(jwtToken);
			}catch(RuntimeException e) {
				response.setStatus(HttpStatus.BAD_REQUEST.value());
				response.setContentType("application/json");
				errorResponse.put("timestamp", String.valueOf(calendar.getTimeInMillis()));
				errorResponse.put("message", "Invalid JWT Token Or Expaired..");
//				errorResponse.put("get new Token", "http://localhost:8083/api/v1/auth/token");
				response.getOutputStream().println(objectMapper.writeValueAsString(errorResponse));
				return;
			}
			
				
		}
		
		// checking username should not be null and (security context shouldbe null -> then request has to authenticate )
		if(userEmail !=null && SecurityContextHolder.getContext().getAuthentication() == null) {
				//Loading User details from DB
				ApplicationUser loadUserByUsername = customUserDetailsService.loadUserByUsername(userEmail);	
				
				//Checking JWT token is valid or not
				if(authService.validateToken(jwtToken, loadUserByUsername)) {
					
					//Setting Authetication object --> HERE UsernamePasswordAuthenticationToken  using AS AUth OBJECT
					UsernamePasswordAuthenticationToken upAuthToken = 
							new UsernamePasswordAuthenticationToken(loadUserByUsername.getEmail(),loadUserByUsername.getPassword(),loadUserByUsername.getAuthorities());
					
					//Setting userdetails and building the request with auth further service calls
					upAuthToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
					
					//Finnaly updating the securit context with Authentication Object
					SecurityContextHolder.getContext().setAuthentication(upAuthToken);
				}
		}
		
		//Bypassing the request to next Filters
		filterChain.doFilter(request, response);
		
				
	}

}
