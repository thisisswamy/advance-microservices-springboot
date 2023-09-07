package com.swamy.learning.userservice.services;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.swamy.learning.userservice.dtos.ApplicationUserResponse;
import com.swamy.learning.userservice.entities.ApplicationUser;
import com.swamy.learning.userservice.entities.UserVerification;
import com.swamy.learning.userservice.exceptions.GenericException;

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
//		String generatedTime = userToken.getGeneratedTime();
//		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//	    LocalDateTime localDateTime = LocalDateTime.parse(generatedTime, formatter);
//	    Date date = new Date().from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
		long difference = (expiredTime/60000) - (createdTime/60000);
		return difference;
	}
	
	

}
