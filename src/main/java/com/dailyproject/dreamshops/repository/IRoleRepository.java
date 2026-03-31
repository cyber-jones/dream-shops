package com.dailyproject.dreamshops.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dailyproject.dreamshops.model.Role;

public interface IRoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);

}
