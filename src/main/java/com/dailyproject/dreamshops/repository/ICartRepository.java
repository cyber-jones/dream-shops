package com.dailyproject.dreamshops.repository;

import com.dailyproject.dreamshops.model.Cart;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ICartRepository extends JpaRepository<Cart,Long> {

    Optional<Cart> findByUserId(Long userId);
}
