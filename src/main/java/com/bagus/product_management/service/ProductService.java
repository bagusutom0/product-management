package com.bagus.product_management.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bagus.product_management.controller.AddProductRequest;
import com.bagus.product_management.controller.EditProductRequest;
import com.bagus.product_management.product.Product;
import com.bagus.product_management.product.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
  private final ProductRepository productRepository;

  public List<Product> getAllProduct() {
    return productRepository.findAll();
  }

  public Product getProductById(Integer id) {
    return productRepository.findById(id)
      .orElseThrow(() -> new RuntimeException("Product not found"));
  }

  public Product addProduct(AddProductRequest request) {
    Product product = Product.builder()
      .name(request.getName())
      .description(request.getDescription())
      .price(request.getPrice())
      .stock(request.getStock())
      .build();
    return productRepository.save(product);
  }

  public Product editProduct(Integer id, EditProductRequest request) {
    Product product = getProductById(id);
    product.setName(request.getName());
    product.setDescription(request.getDescription());
    product.setPrice(request.getPrice());
    product.setStock(request.getStock());
    return productRepository.save(product);
  }

  public void deleteProduct(Integer id) {
    if (!productRepository.existsById(id)) {
      throw new RuntimeException("product not found");
    }
    productRepository.deleteById(id);
  }
}
