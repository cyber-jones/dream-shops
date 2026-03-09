package com.dailyproject.dreamshops.request;

import com.dailyproject.dreamshops.model.Category;
import com.dailyproject.dreamshops.model.Image;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class AddProductRequest {
    private String name;
    private String brand;
    private BigDecimal price;
    private String description;
    private int inventory;

    private Category category;
    private List<Image> images;
}
