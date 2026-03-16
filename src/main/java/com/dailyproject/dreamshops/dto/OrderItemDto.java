package com.dailyproject.dreamshops.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class OrderItemDto {
    private Long Id;
    private int quantity;
    private BigDecimal price;
    private Long productId;
    private String productName;
    private String productBrand;
}
