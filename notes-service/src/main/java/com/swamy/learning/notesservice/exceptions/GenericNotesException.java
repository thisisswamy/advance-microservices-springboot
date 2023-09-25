package com.swamy.learning.notesservice.exceptions;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class GenericNotesException extends RuntimeException {
	
	private String message;
	private HttpStatus httpStatus;
	
	public GenericNotesException(String message, HttpStatus httpStatus) {
		super(message);
		this.message = message;
		this.httpStatus=httpStatus;
		
	}
	

}
