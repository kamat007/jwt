package com.security.services;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.security.entities.User;
import com.security.web.dto.UserRegistrationDto;

public interface CustomUserService extends UserDetailsService{
	User save(UserRegistrationDto registrationDto);
}
