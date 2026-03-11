package com.dailyproject.dreamshops.service.product;

import com.dailyproject.dreamshops.dto.ProductDto;
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
    List<ProductDto> getAllProducts();
    List<ProductDto> getProductsByCategory(String category);
    List<ProductDto> getProductsByBrand(String brand);
    List<ProductDto> getProductsByCategoryAndBrand(String category, String brand);
    List<ProductDto> getProductsByName(String name);
    List<ProductDto> getProductsByBrandAndName(String brand, String name);
    Long countProductsByBrandAndName(String brand, String name);

    ProductDto convertToDto(Product product);
    List<ProductDto> getConvertedProducts(List<Product> products);
}

