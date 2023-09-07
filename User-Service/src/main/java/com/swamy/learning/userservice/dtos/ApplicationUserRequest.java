package com.swamy.learning.userservice.dtos;

import java.util.Date;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class ApplicationUserRequest {

	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private String confirmPassword;
	private String userName;

}
