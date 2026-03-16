package com.dailyproject.dreamshops.dto;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class CartItemDto {
    private Long id;
    private int quantity;
    private BigDecimal unitPrice;
    private ProductDto product;
}
