package com.dailyproject.dreamshops.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.dailyproject.dreamshops.model.Order;

public interface IOrderRepositotry extends JpaRepository<Order, Long> {

    List<Order> findByUserId(Long userId);

}
