package com.swamy.learning.userservice.dtos;

import java.time.Instant;
import java.util.Date;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class ApplicationUserResponse {
	private String firstName;
	private String lastName;
	private String email;
	private String userName;
	private Date createdAt;
	private Date updatedAt;

}
