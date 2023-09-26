package com.swamy.learning.authservice.exceptions;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GenericAuthExceptionHandler {
	
	@ExceptionHandler(value = {GenericAuthException.class})
	public ResponseEntity<Object> errorHandler(GenericAuthException ex){
		Calendar calender = Calendar.getInstance();
		Map<String, String> map = new HashMap<>();
		map.put("status",ex.getHttpStatus().toString() );
		map.put("message", ex.getMessage());
		map.put("timestamp", String.valueOf(calender.getTimeInMillis()));
		return ResponseEntity.badRequest().body(map);
		
	}

}
