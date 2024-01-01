package com.kuti.ProductManagmentSystem.AppManagment.mapper;

import com.kuti.ProductManagmentSystem.AppManagment.controller.ProductController;
import com.kuti.ProductManagmentSystem.AppManagment.dto.responseDto.CategoryResponse;
import com.kuti.ProductManagmentSystem.AppManagment.dto.responseDto.CategoryWithProductsResponse;
import com.kuti.ProductManagmentSystem.AppManagment.dto.responseDto.ProductCategoryNameResponse;
import com.kuti.ProductManagmentSystem.AppManagment.dto.responseDto.ProductResponse;
import com.kuti.ProductManagmentSystem.AppManagment.model.Category;
import com.kuti.ProductManagmentSystem.AppManagment.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
@Component
public class CategoryMapper {

    private static final Logger logger = LoggerFactory.getLogger(CategoryMapper.class);

    public static CategoryWithProductsResponse mapToCategoryWithProductsResponse(Category category) {

        logger.info("Mapping Category to CategoryWithProductsResponse: {}", category);

        List<Product> products = category.getProducts();

        logger.info("Products: {}", products);

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






}
