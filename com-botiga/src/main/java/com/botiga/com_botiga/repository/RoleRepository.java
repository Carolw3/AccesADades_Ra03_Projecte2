package com.botiga.com_botiga.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.botiga.com_botiga.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
}