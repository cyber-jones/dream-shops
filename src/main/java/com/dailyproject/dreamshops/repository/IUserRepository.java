package com.dailyproject.dreamshops.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dailyproject.dreamshops.model.User;

public interface IUserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);

    User findByEmail(String email);

}
