package com.swamy.learning.userservice.events;

import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

import com.swamy.learning.userservice.dtos.ApplicationUserRequest;

import lombok.Data;

@Component
@Data
public class RegistrationCompleteEvent extends ApplicationEvent {
	private static final long serialVersionUID = 1L;
	private ApplicationUserRequest request;

	public RegistrationCompleteEvent(ApplicationUserRequest user) {
		super(user);
	}
	

}
