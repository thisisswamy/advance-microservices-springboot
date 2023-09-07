package com.swamy.learning.userservice.dtos;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ForgotPasswordReq {
	
	private String userName;
	private String email;
	

}
