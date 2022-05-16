package com.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.security.entities.Roles;

@Repository
public interface RoleRepository extends JpaRepository<Roles, Long> {

}
