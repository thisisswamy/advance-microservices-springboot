package com.swamy.learning.userservice.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.swamy.learning.userservice.entities.Roles;

@Repository
public interface RolesRepo extends JpaRepository<Roles, Long> {

}
