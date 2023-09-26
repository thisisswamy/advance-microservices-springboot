package com.swamy.learning.authservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class GenericAuthException  extends RuntimeException{
	
	private String message;
	private HttpStatus httpStatus;
	
	GenericAuthException(){
		super();
	}
	
	public GenericAuthException(String message, HttpStatus httpStatus){
		super(message);
		this.message = message;
		this.httpStatus = httpStatus;
	}

}
