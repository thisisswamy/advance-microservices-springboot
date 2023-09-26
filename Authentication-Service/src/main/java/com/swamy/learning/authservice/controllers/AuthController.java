package com.swamy.learning.authservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.swamy.learning.authservice.dto.AuthenticationReq;
import com.swamy.learning.authservice.services.AuthenticationService;

@RestController
public class AuthController {
	
	@Autowired
	private AuthenticationService  authenticationService;

	@PostMapping("/token")
	public ResponseEntity<Object> generateToken(@RequestBody AuthenticationReq authenticationReq){
		return authenticationService.generateToken(authenticationReq);

		
	}

}
