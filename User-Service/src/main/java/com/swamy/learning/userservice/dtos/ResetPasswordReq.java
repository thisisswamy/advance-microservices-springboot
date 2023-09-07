package com.swamy.learning.userservice.dtos;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResetPasswordReq {
	
	private String email;
	private String password;
	private String confirmPassword;

}
