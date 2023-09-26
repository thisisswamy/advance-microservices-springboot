package com.swamy.learning.authservice.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.swamy.learning.authservice.dto.AuthenticationReq;
import com.swamy.learning.authservice.entity.ApplicationUser;
import com.swamy.learning.authservice.exceptions.GenericAuthException;
import com.swamy.learning.authservice.jwt.JwtAuthService;
import com.swamy.learning.authservice.repo.ApplicationUserRepo;

@Component
public class AuthenticationServiceImpl implements AuthenticationService {
	
	@Autowired
	private JwtAuthService jwtAuthService;
	
	@Autowired
	private ApplicationUserRepo applicationUserRepo;
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	public String authenticateUser(AuthenticationReq authenticationReq) {
		ApplicationUser loadUserByUsername = customUserDetailsService.loadUserByUsername(authenticationReq.getEmail());
		if(loadUserByUsername !=null) {
			if(encoder.matches(authenticationReq.getPassword(), loadUserByUsername.getPassword())){
				String generateToken = jwtAuthService.generateToken(loadUserByUsername);
				return generateToken;
			}
		}
		throw new GenericAuthException("Invalid username/password",HttpStatus.BAD_REQUEST);
		
	}
	@Override
	public ResponseEntity<Object> generateToken(AuthenticationReq authenticationReq) {
			Map<String,String> map = new HashMap<>();
			map.put("token", authenticateUser(authenticationReq));
			return ResponseEntity.ok(map);

	}

}
