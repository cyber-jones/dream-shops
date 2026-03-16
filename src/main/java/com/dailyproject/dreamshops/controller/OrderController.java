package com.dailyproject.dreamshops.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dailyproject.dreamshops.response.ApiResponse;
import com.dailyproject.dreamshops.service.order.IOrderService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/orders")
public class OrderController {
    private final IOrderService orderService;

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse> placeOrder(@PathVariable Long userId) {
        try {
            return ResponseEntity.ok(new ApiResponse("success", orderService.getUseOrder(userId)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse("Order not found for user ID: " + userId, null));
        }
    }
}
