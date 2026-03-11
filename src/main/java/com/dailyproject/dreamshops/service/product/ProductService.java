package com.dailyproject.dreamshops.service.product;

import com.dailyproject.dreamshops.dto.ImageDto;
import com.dailyproject.dreamshops.dto.ProductDto;
import com.dailyproject.dreamshops.exceptions.ResourceNotFoundException;
import com.dailyproject.dreamshops.model.Category;
import com.dailyproject.dreamshops.model.Image;
import com.dailyproject.dreamshops.model.Product;
import com.dailyproject.dreamshops.repository.ICategoryRepository;
import com.dailyproject.dreamshops.repository.IImageRepository;
import com.dailyproject.dreamshops.repository.IProductRepository;
import com.dailyproject.dreamshops.request.AddProductRequest;
import com.dailyproject.dreamshops.request.ProductUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {
    private final IProductRepository productRepository;
    private final ICategoryRepository categoryRepository;
    private final IImageRepository imageRepository;
    private final ModelMapper modelMapper;

    @Override
    public Product addProduct(AddProductRequest request) {
        Category category = Optional.ofNullable(categoryRepository.findByName(request.getCategory()))
                .orElseGet(()-> {
                    Category newCategory = new Category(request.getCategory());
                    return categoryRepository.save(newCategory);
                });

        request.setCategory(category.getName());
        return productRepository.save(createProduct(request, category));
    }

    @Override
    public Product createProduct(AddProductRequest request, Category category) {
        return new Product(
                request.getName(),
                request.getBrand(),
                request.getPrice(),
                request.getDescription(),
                request.getInventory(),
                category
        );
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Product not found"));
    }

    @Override
    public void deleteProductById(Long id) {
        productRepository.findById(id).
                ifPresentOrElse(productRepository::delete,
                        ()-> { throw new ResourceNotFoundException("Product not found"); });
    }

    @Override
    public Product updateProduct(ProductUpdateRequest request, Long productId) {
        return productRepository.findById(productId)
                .map(existingProduct -> updateExistingProduct(existingProduct, request))
                .map(productRepository :: save)
                .orElseThrow(()-> new ResourceNotFoundException("Product not found"));
    }

    private Product updateExistingProduct(Product existingProduct, ProductUpdateRequest request) {
        existingProduct.setName(request.getName());
        existingProduct.setBrand(request.getBrand());
        existingProduct.setPrice(request.getPrice());
        existingProduct.setInventory(request.getInventory());
        existingProduct.setPrice(request.getPrice());

        Category category = categoryRepository.findByName(request.getCategory().getName());
        existingProduct.setCategory(category);

        return existingProduct;
    }

    @Override
    public List<ProductDto> getAllProducts() {
        List<Product> products = productRepository.findAll();
        List<ProductDto> productDtos = getConvertedProducts(products);
        return productDtos;
    }

    @Override
    public List<ProductDto> getProductsByCategory(String category) {
        List<Product> products = productRepository.findByCategoryName(category);
        List<ProductDto> productDtos = getConvertedProducts(products);
        return productDtos;
    }

    @Override
    public List<ProductDto> getProductsByBrand(String brand) {
        List<Product> products = productRepository.findByBrand(brand);
        List<ProductDto> productDtos = getConvertedProducts(products);
        return productDtos;
    }

    @Override
    public List<ProductDto> getProductsByCategoryAndBrand(String category, String brand) {
        List<Product> products = productRepository.findByCategoryNameAndBrand(category, brand);
        List<ProductDto> productDtos = getConvertedProducts(products);
        return productDtos;
    }

    @Override
    public List<ProductDto> getProductsByName(String name) {
        List<Product> products = productRepository.findByName(name);
        List<ProductDto> productDtos = getConvertedProducts(products);
        return productDtos;
    }

    @Override
    public List<ProductDto> getProductsByBrandAndName(String brand, String name) {
        List<Product> products = productRepository.findByBrandAndName(brand, name);
        List<ProductDto> productDtos = getConvertedProducts(products);
        return productDtos;
    }

    @Override
    public Long countProductsByBrandAndName(String brand, String name) {
        return productRepository.countByBrandAndName(brand, name);
    }

    @Override
    public List<ProductDto> getConvertedProducts(List<Product> products) {
        return products.stream().map(this::convertToDto).toList();
    }

    @Override
    public ProductDto convertToDto(Product product) {
        ProductDto productDto = modelMapper.map(product, ProductDto.class);
        List<Image> images = imageRepository.findByProductId(productDto.getId());
        List<ImageDto> imageDtos = images.stream()
                .map(image -> modelMapper.map(image, ImageDto.class))
                .toList();
        productDto.setImages(imageDtos);
        return productDto;
    }
}
