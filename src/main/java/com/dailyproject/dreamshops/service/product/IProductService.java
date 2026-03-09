package com.dailyproject.dreamshops.service.product;

import com.dailyproject.dreamshops.model.Category;
import com.dailyproject.dreamshops.model.Product;
import com.dailyproject.dreamshops.request.AddProductRequest;
import com.dailyproject.dreamshops.request.ProductUpdateRequest;

import java.util.List;

public interface IProductService {
    Product addProduct(AddProductRequest request);
    Product createProduct(AddProductRequest request, Category category);
    Product getProductById(Long id);
    void deleteProductById(Long id);
    Product updateProduct(ProductUpdateRequest request, Long productId);
    List<Product> getAllProducts();
    List<Product> getProductsByCategory(String category);
    List<Product> getProductsByBrand(String brand);
    List<Product> getProductsByCategoryAndBrand(String category, String brand);
    List<Product> getProductsByName(String name);
    List<Product> getProductsByBrandAndName(String brand, String name);
    Long countProductsByBrandAndName(String brand, String name);
}
