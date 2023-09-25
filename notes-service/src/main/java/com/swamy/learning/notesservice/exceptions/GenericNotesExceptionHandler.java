package com.swamy.learning.notesservice.exceptions;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GenericNotesExceptionHandler {
	
	
	@ExceptionHandler(value = {GenericNotesException.class})
	public ResponseEntity<Object> setException(GenericNotesException ex){
		Map<String,String> map = new HashMap<>();
		map.put("status", ex.getHttpStatus().toString());
		map.put("message", ex.getMessage());
		map.put("timestamp", String.valueOf(Calendar.getInstance().getTimeInMillis()));
		return ResponseEntity.badRequest().body(map);
		
	}

}
