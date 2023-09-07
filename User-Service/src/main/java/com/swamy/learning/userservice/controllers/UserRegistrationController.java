package com.swamy.learning.userservice.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.swamy.learning.userservice.dtos.ApplicationUserRequest;
import com.swamy.learning.userservice.dtos.ForgotPasswordReq;
import com.swamy.learning.userservice.dtos.ResetPasswordReq;
import com.swamy.learning.userservice.services.ApplicationUtilities;
import com.swamy.learning.userservice.services.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@Tag(name = "User Functionalities")
public class UserRegistrationController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ApplicationUtilities utilities;
	
	@Operation(description = "User Registration", summary = "This end point for user registration/creation")
	@PostMapping("/register")
	public ResponseEntity<Object> userRegistration(@RequestBody ApplicationUserRequest request, HttpServletRequest httpReq) {
		return userService.register(request,httpReq);
	
	}
	
	@Operation(description = "User Registration Activation", summary = "This is for active the user registration")
	@GetMapping("/verify")
	public ResponseEntity<Object> verifyUserRegistration(@RequestParam("token") String token){
		return userService.verifyUserReg(token);
	}
	
	@Operation(description = "User Resend Activation Link", summary = "This is for resend user activation link")
	@PostMapping("/resend/verifyLink")
	public ResponseEntity<Object> resendVerificationLink(@RequestBody Map<String,String> userNameMap, HttpServletRequest httpReq){
		String userName = userNameMap.get("userName");
		return userService.resendVerificationLink(userName,httpReq);
	}
	
	@Operation(description = "User Forget password", summary = "This is for forgot password")
	@PostMapping("/forgot-password")
	public ResponseEntity<Object> forgotPassword(@RequestBody ForgotPasswordReq req){
		return userService.forgotPassWord(req);	
	}
	
	@Operation(description = "User Reset password", summary = "This is for reset password")
	@PostMapping("/reset-password")
	public ResponseEntity<Object> resePassword(@RequestBody ResetPasswordReq req){
		return userService.resetPassWord(req);	
	}
	

}
