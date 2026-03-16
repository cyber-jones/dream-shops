package com.dailyproject.dreamshops.service.order;

import com.dailyproject.dreamshops.model.Order;

public interface IOrderService {
    Order placeOrder(Long id);
    Order getOrderById(Long orderId);
    Order getUseOrder(Long userId);
}
