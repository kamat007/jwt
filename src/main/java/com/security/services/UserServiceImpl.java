package com.security.services;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.security.entities.Roles;
import com.security.entities.User;
import com.security.repository.UserRepository;
import com.security.web.dto.UserRegistrationDto;

@Service
public class UserServiceImpl implements CustomUserService{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	public BCryptPasswordEncoder customEncoder;
	
	public UserServiceImpl(UserRepository userRepository) {
		super();
		this.userRepository=userRepository;
	}
	
	@Override
	public User save(UserRegistrationDto registrationDto) {
		User user = new User(registrationDto.getFirstName(),
				registrationDto.getLastName(),
				registrationDto.getEmail(),
				customEncoder.encode(registrationDto.getPassword()),
				Arrays.asList(new Roles("ROLE_USER")));
		return userRepository.save(user);
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(email);
		if(user==null) {
			throw new UsernameNotFoundException("Invalid Email or Password.");
		}
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
	}

	// this is to get the roles which is used in loadUserByUsername
	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Roles> roles){
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
	}
}
