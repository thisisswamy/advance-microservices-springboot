package com.swamy.learning.authservice.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.swamy.learning.authservice.entity.ApplicationUser;
import com.swamy.learning.authservice.exceptions.GenericAuthException;
import com.swamy.learning.authservice.repo.ApplicationUserRepo;

@Service
public class CustomUserDetailsService implements UserDetailsService{
	
	@Autowired
	private ApplicationUserRepo applicationUserRepo;

	@Override
	public ApplicationUser loadUserByUsername(String email) throws UsernameNotFoundException {
		
		Optional<ApplicationUser> optionalUser = applicationUserRepo.findByEmail(email);
		if(optionalUser.isPresent()) {	
			return optionalUser.get();
		}
		throw new GenericAuthException("invalid username/password",HttpStatus.UNAUTHORIZED);
	}

}
