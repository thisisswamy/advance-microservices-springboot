package com.swamy.learning.userservice.services;


import java.util.Calendar;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.swamy.learning.userservice.dtos.ApplicationUserRequest;
import com.swamy.learning.userservice.dtos.ApplicationUserResponse;
import com.swamy.learning.userservice.dtos.ForgotPasswordReq;
import com.swamy.learning.userservice.dtos.ResetPasswordReq;
import com.swamy.learning.userservice.entities.ApplicationUser;
import com.swamy.learning.userservice.entities.Roles;
import com.swamy.learning.userservice.entities.UserVerification;
import com.swamy.learning.userservice.events.RegistrationCompleteEvent;
import com.swamy.learning.userservice.exceptions.GenericException;
import com.swamy.learning.userservice.repos.ApplicationUserRepo;
import com.swamy.learning.userservice.repos.UserVerificationRepo;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class UserServiceImpl implements UserService {
	
	private final static long EXPIRY_DURATION_IN_MIN =5;

	private static String generateVerificationLink;
	
	@Autowired
	private ApplicationUserRepo applicationUserRepo;
	
	@Autowired
	private ApplicationUtilities utilities;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserVerificationRepo userVerificationRepo;

	@Autowired
	private ApplicationEventPublisher publisher;

	@Autowired
	private JavaMailSender mailSender;


	

	@Override
	public ResponseEntity<Object> register(ApplicationUserRequest request,HttpServletRequest req){
	
		if(!isAllUserFieldsFilled(request)) {
			throw new GenericException("All fields are mandatory !Please fill all the feilds",HttpStatus.BAD_REQUEST);
		}
		ApplicationUser findByEmail = applicationUserRepo.findByEmail(request.getEmail());
		Optional<ApplicationUser> optionalUser  = applicationUserRepo.findByUserName(request.getUserName());

		if(findByEmail != null || optionalUser.isPresent()) {
			throw new GenericException("User already existed ! please login",HttpStatus.BAD_REQUEST);
		}
		//Create an empty object
		ApplicationUser user = setApplicationUserProperties(request);		
		applicationUserRepo.save(user);

		


		//Return UserResponse;
		ApplicationUserResponse applicationUserResponse = utilities.toApplicationUserResponse(user);
		String applicationBasePath = utilities.getApplicationBasePath(req);
		generateVerificationLink = applicationBasePath + generateVerificationLink(request.getUserName());
		
		//publishing an email event
		publisher.publishEvent(new RegistrationCompleteEvent(request));
		//returning activation Link
		// return ResponseEntity.ok(generateVerificationLink);
		return ResponseEntity.ok("Verification link sent to an email please complete verification..");
	}

	//E-mail Listner
	@EventListener(RegistrationCompleteEvent.class)
	public void triggerVerificationEmail(RegistrationCompleteEvent event){
		ApplicationUserRequest user = (ApplicationUserRequest) event.getSource();
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setFrom(DataConstants.fromEmail);
		mailMessage.setTo(user.getEmail());
		mailMessage.setSubject("Activation link");
		mailMessage.setText("Dear "+ user.getFirstName() +"\n\n\nComplete your registration through Following link : "+ generateVerificationLink + "\n\n"+DataConstants.salutationNote);
		mailSender.send(mailMessage);
	
	}
	boolean isAllUserFieldsFilled(ApplicationUserRequest request){
		if(request.getFirstName() == null || request.getFirstName().equals("") ||
				request.getLastName() == null || request.getLastName().equals("") || 
				request.getEmail() == null || request.getEmail().equals("") ||
				request.getPassword() == null || request.getPassword().equals("") ||
				request.getConfirmPassword() == null || request.getConfirmPassword().equals("")) {
			return false;
		}
		return true;
	}

	@Override
	public ResponseEntity<Object> verifyUserReg(String token) {
		Optional<UserVerification> userToken=null;
	
		try {
			userToken = userVerificationRepo.getByToken(token);
		}catch(RuntimeException e) {
			throw new GenericException("Invalid Registration Link/No user registered !!",HttpStatus.BAD_REQUEST);
		}
		if(userToken.isPresent()){
			long tokenExpiryTime = utilities.getTokenExpiryTime(userToken.get());
			if(tokenExpiryTime> EXPIRY_DURATION_IN_MIN) {
				userVerificationRepo.delete(userToken.get());
				throw new GenericException("Activation link has been expaired. ! please request for new",HttpStatus.BAD_REQUEST);
			}
			if(setUserActivation(userToken.get())) {
				userVerificationRepo.delete(userToken.get());
				return ResponseEntity.ok("User activation successfull ! please login");
			}else {
				return ResponseEntity.ok("Invalid activation/invalid user details ! Please register/request for another one");
			}
			
		}else {
			throw new GenericException("Invalid Activation Link ! Please re generate another.",HttpStatus.BAD_REQUEST);
		}
			
	}
	public boolean setUserActivation(UserVerification token){
		Optional<ApplicationUser> optionalUser = applicationUserRepo.findByUserName(token.getUserName());
		if(optionalUser.isPresent()) {
			ApplicationUser user = optionalUser.get();
			user.setAccountEnabled(true);
			user.setAccountNonExpired(true);
			user.setAccountNonLocked(true);
			applicationUserRepo.save(user);
			return true;
		}
		return false;
		
	}
	
	public String generateVerificationLink(String userName) {
		String randomString = UUID.randomUUID().toString();
		UserVerification link = new UserVerification();
		Calendar calendar = Calendar.getInstance();
		link.setToken(randomString);
		link.setUserName(userName);
		link.setGeneratedTime(calendar.getTime());
		userVerificationRepo.save(link);
		return  randomString;
	}
	ApplicationUser setApplicationUserProperties(ApplicationUserRequest request){
		Calendar calendar = Calendar.getInstance();
		Set<Roles> roleSet = new HashSet<>();
		Roles role = new Roles();
		role.setRoleDescription("read and view");
		role.setRoleName("USER_ROLE");
		roleSet.add(role);
		ApplicationUser user = new ApplicationUser();
		user.setFirstName(request.getFirstName());
		user.setLastName(request.getLastName());
		user.setUserName(request.getUserName());
		user.setEmail(request.getEmail());
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		user.setConfirmPassword(passwordEncoder.encode(request.getConfirmPassword()));
		user.setAccountNonExpired(false);
		user.setAccountEnabled(false);
		user.setAccountNonLocked(false);
		user.setMfaEnabled(false);
		user.setCredentialsNonExpired(false);
		user.setCreatedAt(calendar.getTime());
		user.setUpdatedAt(calendar.getTime());	
		user.setRoles(roleSet);
		return user;	
	}

	@Override
	public ResponseEntity<Object> resendVerificationLink(String userName, HttpServletRequest httpReq) {
		Optional<ApplicationUser> optionalUser = applicationUserRepo.findByUserName(userName);
		if(optionalUser.isPresent()) {
			String applicationBasePath = utilities.getApplicationBasePath(httpReq);
			String reGeneratedVerificationLink = applicationBasePath + generateVerificationLink(userName);
			return ResponseEntity.ok(reGeneratedVerificationLink);
		}
		throw new GenericException("Invalid userName",HttpStatus.BAD_REQUEST);
	}

	@Override
	public ResponseEntity<Object> forgotPassWord(ForgotPasswordReq req) {
		ApplicationUser findByEmail = applicationUserRepo.findByEmail(req.getEmail());
		if(findByEmail != null) {
			return ResponseEntity.ok(req);
		}
		throw new GenericException("Invalid Username/Email", HttpStatus.BAD_REQUEST);
	}

	@Override
	public ResponseEntity<Object> resetPassWord(ResetPasswordReq req) {
		ApplicationUser findByEmail = applicationUserRepo.findByEmail(req.getEmail());
		Calendar calendar = Calendar.getInstance();
		if(findByEmail != null) {
			findByEmail.setPassword(passwordEncoder.encode(req.getPassword()));
			findByEmail.setConfirmPassword(passwordEncoder.encode(req.getConfirmPassword()));
			findByEmail.setUpdatedAt(calendar.getTime());
			applicationUserRepo.save(findByEmail);
			return ResponseEntity.ok("Password changed successfully ! Please login");
		}
		throw new GenericException("Unable process the data",HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	

}
