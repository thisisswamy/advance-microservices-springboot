package com.swamy.learning.userservice.services;

import java.util.Calendar;

import org.springframework.stereotype.Component;

import com.swamy.learning.userservice.dtos.ApplicationUserResponse;
import com.swamy.learning.userservice.entities.ApplicationUser;
import com.swamy.learning.userservice.entities.UserVerification;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class ApplicationUtilities {


	
	
	public ApplicationUserResponse toApplicationUserResponse(ApplicationUser user) {
		ApplicationUserResponse applicationUserResponse = new ApplicationUserResponse();
		applicationUserResponse.setFirstName(user.getFirstName());
		applicationUserResponse.setLastName(user.getLastName());
		applicationUserResponse.setUserName(user.getUsername());
		applicationUserResponse.setEmail(user.getEmail());
		applicationUserResponse.setCreatedAt(user.getCreatedAt());
		applicationUserResponse.setUpdatedAt(user.getUpdatedAt());
		return applicationUserResponse;

	}

	public String getApplicationBasePath(HttpServletRequest req) {
		String BaseUrl = "http://"+req.getServerName()+":" + req.getServerPort() + req.getContextPath() +"/verify?token=";
		return BaseUrl;
		
	}

	public long getTokenExpiryTime(UserVerification userToken) {
		Calendar calender = Calendar.getInstance(); 
		long expiredTime = calender.getTimeInMillis();	
		long createdTime = userToken.getGeneratedTime().getTime();
		long difference = (expiredTime/60000) - (createdTime/60000);
		return difference;
	}

	
	
	

}
