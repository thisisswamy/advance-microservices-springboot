package com.swamy.learning.userservice.dtos;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class VerificationLink {
	String token;

}
