package com.swamy.learning.authservice.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.swamy.learning.authservice.dto.AuthenticationReq;

@Service
public interface AuthenticationService {

	ResponseEntity<Object> generateToken(AuthenticationReq authenticationReq);

}
