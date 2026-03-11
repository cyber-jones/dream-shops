package com.dailyproject.dreamshops.controller;

import com.dailyproject.dreamshops.dto.ProductDto;
import com.dailyproject.dreamshops.exceptions.ResourceNotFoundException;
import com.dailyproject.dreamshops.model.Product;
import com.dailyproject.dreamshops.request.AddProductRequest;
import com.dailyproject.dreamshops.request.ProductUpdateRequest;
import com.dailyproject.dreamshops.response.ApiResponse;
import com.dailyproject.dreamshops.service.product.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/products")
public class ProductController {
    private final IProductService productService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllProducts() {
        List<ProductDto> productDtos = productService.getAllProducts();
        return  ResponseEntity.ok(new ApiResponse("Success", productDtos));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable Long productId) {
        try {
            Product product = productService.getProductById(productId);
            ProductDto productDto = productService.convertToDto(product);

            return  ResponseEntity.ok(new ApiResponse("Success", productDto));
        } catch (ResourceNotFoundException e) {
            return   ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addProduct(@RequestBody AddProductRequest request) {
        try {
            Product theProduct = productService.addProduct(request);
            return   ResponseEntity.ok(new ApiResponse("Product added successfully", theProduct));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ApiResponse> updateProduct(@RequestBody ProductUpdateRequest request, @PathVariable Long productId) {
        try {
            Product theProduct = productService.updateProduct(request,productId);
            return  ResponseEntity.ok(new ApiResponse("Product updated successfully", theProduct));
        } catch (ResourceNotFoundException e) {
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long productId) {
        try {
            productService.deleteProductById(productId);
            return  ResponseEntity.ok(new ApiResponse("Product deleted successfully", productId));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/by/name")
    public ResponseEntity<ApiResponse> findProductByName(@RequestParam String name) {
        try {
            List<ProductDto> productDtos = productService.getProductsByName(name);
            if (productDtos.isEmpty())
                return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("No product found", null));

            return  ResponseEntity.ok(new ApiResponse("Success", productDtos));
        } catch (Exception e) {
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/by/brand")
    public ResponseEntity<ApiResponse> findProductByBrand(@RequestParam String brand) {
        try {
            List<ProductDto> productDtos = productService.getProductsByBrand(brand);
            if (productDtos.isEmpty())
                return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("No product found", null));

            return  ResponseEntity.ok(new ApiResponse("Success", productDtos));
        } catch (Exception e) {
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/by/category/{category}")
    public ResponseEntity<ApiResponse> findProductByCategory(@PathVariable String category) {
        try {
            List<ProductDto> productDtos = productService.getProductsByCategory(category);
            if (productDtos.isEmpty())
                return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("No product found", null));

            return  ResponseEntity.ok(new ApiResponse("Success", productDtos));
        } catch (Exception e) {
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/by/brand-and-name")
    public ResponseEntity<ApiResponse> getProductByBrandAndName(@RequestParam String brand, @RequestParam String productName) {
        try {
            List<ProductDto> productDtos = productService.getProductsByBrandAndName(brand, productName);
            if (productDtos.isEmpty())
                return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("No product found", null));

            return  ResponseEntity.ok(new ApiResponse("Success", productDtos));
        } catch (Exception e) {
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/by/category-and-brand")
    public ResponseEntity<ApiResponse> getProductByCategoryAndBrand(@RequestParam String categoryName, @RequestParam String brand) {
        try {
            List<ProductDto> productDtos = productService.getProductsByCategoryAndBrand(categoryName, brand);
            if (productDtos.isEmpty())
                return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("No product found", null));

            return  ResponseEntity.ok(new ApiResponse("Success", productDtos));
        } catch (Exception e) {
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/count/by/brand-and-name")
    public ResponseEntity<ApiResponse> countProductsByBrandAndName(@RequestParam String brand, @RequestParam String name) {
        try {
            Long productCount = productService.countProductsByBrandAndName(brand, name);

            return  ResponseEntity.ok(new ApiResponse("Product count", productCount));
        } catch (Exception e) {
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }
}
