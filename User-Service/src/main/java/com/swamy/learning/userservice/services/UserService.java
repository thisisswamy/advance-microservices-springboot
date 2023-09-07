package com.swamy.learning.userservice.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.swamy.learning.userservice.dtos.ApplicationUserRequest;
import com.swamy.learning.userservice.dtos.ForgotPasswordReq;
import com.swamy.learning.userservice.dtos.ResetPasswordReq;

import jakarta.servlet.http.HttpServletRequest;

@Service
public interface UserService {

	ResponseEntity<Object> register(ApplicationUserRequest request,HttpServletRequest req);

	ResponseEntity<Object> verifyUserReg(String token);

	ResponseEntity<Object> resendVerificationLink(String userName, HttpServletRequest httpReq);

	ResponseEntity<Object> forgotPassWord(ForgotPasswordReq req);

	ResponseEntity<Object> resetPassWord(ResetPasswordReq req);

}
