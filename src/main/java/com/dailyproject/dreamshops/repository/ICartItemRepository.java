package com.dailyproject.dreamshops.repository;

import com.dailyproject.dreamshops.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICartItemRepository extends JpaRepository<CartItem, Long> {
    void deleteAllByCartId(Long id);
}
