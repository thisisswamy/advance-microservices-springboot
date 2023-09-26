package com.swamy.learning.authservice.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.swamy.learning.authservice.entity.ApplicationUser;

@Repository
public interface ApplicationUserRepo extends JpaRepository<ApplicationUser, Long> {
	
	
	Optional<ApplicationUser> findByEmail(String email);

}
