package com.bagus.product_management.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bagus.product_management.product.Product;
import com.bagus.product_management.service.ProductService;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
  private final ProductService productService;

  @GetMapping("/")
  public ResponseEntity<List<Product>> getAllProducts() {
      return ResponseEntity.ok(productService.getAllProduct());
  }

  @PostMapping("/add")
  public ResponseEntity<Product> addProduct(@RequestBody AddProductRequest request) {
      return ResponseEntity.ok(productService.addProduct(request));
  }
  
  @PutMapping("/edit/{id}")
  public ResponseEntity<Product> editProduct(
    @PathVariable String id, 
    @RequestBody EditProductRequest request
  ) {
      return ResponseEntity.ok(productService.editProduct(Integer.valueOf(id), request));
  }

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<Void> deleteProduct(@PathVariable Integer id) {
    productService.deleteProduct(id);
    return ResponseEntity.noContent().build();
  }
}
