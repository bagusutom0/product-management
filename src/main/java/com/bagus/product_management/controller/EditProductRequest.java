package com.bagus.product_management.controller;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EditProductRequest {
  private String name;
  private String description;
  private Double price;
  private int stock;
}
