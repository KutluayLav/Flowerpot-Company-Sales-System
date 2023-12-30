package com.kuti.ProductManagmentSystem.AppManagment.mapper;

import com.kuti.ProductManagmentSystem.AppManagment.dto.responseDto.CategoryResponse;
import com.kuti.ProductManagmentSystem.AppManagment.dto.responseDto.CategoryWithProductsResponse;
import com.kuti.ProductManagmentSystem.AppManagment.dto.responseDto.ProductResponse;
import com.kuti.ProductManagmentSystem.AppManagment.model.Category;
import com.kuti.ProductManagmentSystem.AppManagment.model.Product;

import java.util.Set;
import java.util.stream.Collectors;

public class CategoryMapper {

    public static CategoryResponse mapToCategoryResponse(Category category){
        return CategoryResponse.builder()
                .id(category.getId())
                .productList(category.getProducts())
                .name(category.getName())
                .description(category.getDescription())
                .message("Category:"+category.getName()+" Displayed")
                .build();
    }

    public static CategoryWithProductsResponse mapToCategoryWithProductsResponse(Category category) {
        Set<Product> products = category.getProducts();
        Set<ProductResponse> productResponses = products.stream()
                .map(ProductMapper::mapToProductResponse)
                .collect(Collectors.toSet());

        return CategoryWithProductsResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .productList(productResponses)
                .build();
    }


}
