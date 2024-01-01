package com.kuti.ProductManagmentSystem.AppManagment.dto.responseDto;

import com.kuti.ProductManagmentSystem.AppManagment.model.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryResponse {

    private String message;

    private Long id;

    private String name;

    private String description;

    private List<ProductCategoryNameResponse> productList;

}
