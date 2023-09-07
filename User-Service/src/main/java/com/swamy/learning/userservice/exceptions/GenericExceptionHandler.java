package com.swamy.learning.userservice.exceptions;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GenericExceptionHandler {
	
	private Map<String,String> responseObj = new HashMap<>();
	
	@ExceptionHandler(value = {GenericException.class})
	public ResponseEntity<Object> handler(GenericException genericException){
		responseObj.put("status", genericException.getHttpStatus().toString());
		responseObj.put("message", genericException.getMessage());
		responseObj.put("timestamp", Instant.now().toString());
		return ResponseEntity.badRequest().body(responseObj);
	}

}
