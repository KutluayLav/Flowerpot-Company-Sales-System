package com.kuti.ProductManagmentSystem.AppManagment.mapper;
import com.kuti.ProductManagmentSystem.AppManagment.dto.responseDto.*;
import com.kuti.ProductManagmentSystem.AppManagment.model.Category;
import com.kuti.ProductManagmentSystem.AppManagment.model.Product;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

import static com.kuti.ProductManagmentSystem.AppManagment.mapper.ProductMapper.mapToProductNameResponseList;

@Component
public class CategoryMapper {
    public static List<CategoryWithProductsResponse> mapToCategoryWithProductsNameResponse(List<Category> categories) {
        return categories.stream()
                .map(category -> CategoryWithProductsResponse.builder()
                        .id(category.getId())
                        .name(category.getName())
                        .description(category.getDescription())
                        .productList(mapToProductNameResponseList(category.getProducts()))
                        .build())
                .collect(Collectors.toList());
    }

    public static CategoryResponse mapToCategoryResponse(Category category) {
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .message("Category: " + category.getName() + " Displayed")
                .productList(mapToProductCategoryNameResponses(category.getProducts()))
                .build();
    }

    private static List<ProductCategoryNameResponse> mapToProductCategoryNameResponses(List<Product> products) {
        return products.stream()
                .map(product -> ProductCategoryNameResponse.builder()
                        .id(product.getId())
                        .name(product.getProductName())
                        .description(product.getDescription())
                        .message("Product: " + product.getProductName() + " Displayed")
                        .price(product.getPrice())
                        .quantity(product.getQuantity())
                        .fileData(product.getFileData())
                        .category(product.getCategory().getName())
                        .build())
                .collect(Collectors.toList());
    }

    public static List<CategoryNameResponse> mapToCategoryNameResponses(List<Category> categories) {
        return categories.stream()
                .map(category -> CategoryNameResponse.builder()
                        .id(category.getId())
                        .categoryName(category.getName())
                        .categoryDescription(category.getDescription())
                        .message("Category Displayed:"+category.getName())
                        .build())
                .collect(Collectors.toList());
    }







}
