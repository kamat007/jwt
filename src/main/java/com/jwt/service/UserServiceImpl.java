package com.jwt.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jwt.entities.Role;
import com.jwt.entities.User;
import com.jwt.repository.RoleRepo;
import com.jwt.repository.UserRepo;

@Service
@Transactional
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepo userRepo;
	@Autowired
	private RoleRepo roleRepo;

	Logger logger = LoggerFactory.getLogger("Sample Logger");
	
	@Override
	public User saveUser(User user) {
		logger.info("Saving new User {} info to database.", user.getName());
		return userRepo.save(user);
	}

	@Override
	public Role saveRole(Role role) {
		logger.info("Save new role {} to the database.", role.getName());
		return roleRepo.save(role);
	}

	@Override
	public void addRoleToUser(String username, String roleName) {
		logger.info("Adding User {} with Role {} to the database", username, roleName);
		User user = userRepo.findByUsername(username);
		Role role = roleRepo.findByName(roleName);
		user.getRoles().add(role);
	}

	@Override
	public User getUser(String username) {
		logger.info("Fetching user {}", username);
		return userRepo.findByUsername(username);
	}

	@Override
	public List<User> getUsers() {
		logger.info("Fetching all Users");
		return userRepo.findAll();
	}

}
