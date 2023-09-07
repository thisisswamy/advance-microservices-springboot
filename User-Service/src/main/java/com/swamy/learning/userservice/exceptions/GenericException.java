package com.swamy.learning.userservice.exceptions;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class GenericException  extends RuntimeException{
	
	private String mesage;
	private HttpStatus httpStatus;
	
	public GenericException(String message) {
		super(message);
	}
	
	public GenericException(String message,HttpStatus httpStatus) {
		super(message);
		this.mesage=message;
		this.httpStatus =httpStatus;
				
	}
	
	

}
