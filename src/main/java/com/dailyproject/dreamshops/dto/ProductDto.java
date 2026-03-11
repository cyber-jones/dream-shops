package com.dailyproject.dreamshops.dto;

import com.dailyproject.dreamshops.model.Category;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ProductDto {
    private Long id;
    private String name;
    private String brand;
    private BigDecimal price;
    private String description;
    private int inventory;

    private Category category;
    private List<ImageDto> images;
}
