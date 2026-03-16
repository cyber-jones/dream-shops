package com.dailyproject.dreamshops.service.order;

import java.util.List;

import com.dailyproject.dreamshops.dto.OrderDto;
import com.dailyproject.dreamshops.model.Order;

public interface IOrderService {
    Order placeOrder(Long id);
    OrderDto getOrderById(Long orderId);
    List<OrderDto> getUserOrders(Long userId);
    OrderDto convertToDto(Order order);
}
