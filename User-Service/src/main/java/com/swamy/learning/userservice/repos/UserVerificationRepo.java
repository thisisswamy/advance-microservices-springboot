package com.swamy.learning.userservice.repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.swamy.learning.userservice.entities.UserVerification;

@Repository
public interface UserVerificationRepo extends JpaRepository<UserVerification, Long> {

	Optional<UserVerification> getByToken(String token);

}
