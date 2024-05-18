package com.project.fin.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.fin.model.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

}