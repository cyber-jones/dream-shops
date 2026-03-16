package com.dailyproject.dreamshops.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.dailyproject.dreamshops.model.Order;

public interface IOrderRepositotry extends JpaRepository<Order, Long> {

    Optional<Order> findByUserId(Long userId);

}
