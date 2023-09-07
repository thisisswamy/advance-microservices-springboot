package com.swamy.learning.userservice.repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.swamy.learning.userservice.entities.ApplicationUser;

@Repository
public interface ApplicationUserRepo extends JpaRepository<ApplicationUser, Long> {
	
	ApplicationUser findByEmail(String email);
	Optional<ApplicationUser> findByUserName(String userName);

}
