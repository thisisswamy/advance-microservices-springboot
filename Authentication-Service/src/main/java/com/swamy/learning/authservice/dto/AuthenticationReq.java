package com.swamy.learning.authservice.dto;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class AuthenticationReq {
	
	private String email;
	private String password;

}
