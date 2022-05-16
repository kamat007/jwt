package com.jwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jwt.entities.Role;

public interface RoleRepo extends JpaRepository<Role, Long> {

	Role findByName(String name);
}
