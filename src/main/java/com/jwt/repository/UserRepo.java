package com.jwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jwt.entities.User;

public interface UserRepo extends JpaRepository<User, Long> {

	User findByUsername(String username);
}
