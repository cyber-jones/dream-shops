package com.dailyproject.dreamshops.service.order;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.dailyproject.dreamshops.dto.OrderDto;
import com.dailyproject.dreamshops.enums.OrderStatus;
import com.dailyproject.dreamshops.exceptions.ResourceNotFoundException;
import com.dailyproject.dreamshops.model.Cart;
import com.dailyproject.dreamshops.model.Order;
import com.dailyproject.dreamshops.model.OrderItem;
import com.dailyproject.dreamshops.model.Product;
import com.dailyproject.dreamshops.repository.IOrderRepositotry;
import com.dailyproject.dreamshops.repository.IProductRepository;
import com.dailyproject.dreamshops.service.cart.ICartService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {
    private final IOrderRepositotry orderRepository;
    private final IProductRepository productRepository;
    private final ICartService cartService;
    private final ModelMapper modelMapper;

    @Override
    public Order placeOrder(Long userId) {
        Cart cart = cartService.getCartByUserId(userId);
        Order order = createOrder(cart);

        List<OrderItem> orderItems = createOrderItems(order, cart);
        order.setOrderItems(new HashSet<>(orderItems));
        order.setTotalAmount(calculateTotalAmount(orderItems));
        
        Order savedOrder = orderRepository.save(order);
        cartService.clearCart(cart.getId());

        return savedOrder;
    }
    
    @Override
    public OrderDto getOrderById(Long orderId) {
        return orderRepository.findById(orderId).map(this::convertToDto)
            .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
    }

    @Override
    public List<OrderDto> getUserOrders(Long userId) {
        return orderRepository.findByUserId(userId).stream().map(this::convertToDto).toList();
    }

    private BigDecimal calculateTotalAmount(List<OrderItem> orderItems) {
        return orderItems.stream()
            .map(item -> item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private Order createOrder(Cart cart) {
        Order order = new Order();
        order.setUser(cart.getUser());
        order.setOrderStatus(OrderStatus.PENDING);
        order.setOrderDate(LocalDate.now());
        return order;
    }

    private List<OrderItem> createOrderItems(Order order, Cart cart) {
        return cart.getCartItems().stream()
            .map(cartItem -> {
                Product product = cartItem.getProduct();
                product.setInventory(product.getInventory() - cartItem.getQuantity());
                productRepository.save(product); 
                return new OrderItem(cartItem.getQuantity(), cartItem.getUnitPrice(), order, product);
            }).toList();
    }

    @Override
    public OrderDto convertToDto(Order order) {
        return modelMapper.map(order, OrderDto.class);
    }
}
