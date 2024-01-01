package com.kuti.ProductManagmentSystem.AppManagment.mapper;

import com.kuti.ProductManagmentSystem.AppManagment.controller.ProductController;
import com.kuti.ProductManagmentSystem.AppManagment.dto.responseDto.CategoryResponse;
import com.kuti.ProductManagmentSystem.AppManagment.dto.responseDto.CategoryWithProductsResponse;
import com.kuti.ProductManagmentSystem.AppManagment.dto.responseDto.ProductResponse;
import com.kuti.ProductManagmentSystem.AppManagment.model.Category;
import com.kuti.ProductManagmentSystem.AppManagment.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CategoryMapper {

    private static final Logger logger = LoggerFactory.getLogger(CategoryMapper.class);

    public static CategoryResponse mapToCategoryResponse(Category category){

        logger.info("Mapping Category to CategoryResponse: {}", category);

        return CategoryResponse.builder()
                .id(category.getId())
                .productList(category.getProducts())
                .name(category.getName())
                .description(category.getDescription())
                .message("Category:"+category.getName()+" Displayed")
                .build();
    }

    public static CategoryWithProductsResponse mapToCategoryWithProductsResponse(Category category) {

        logger.info("Mapping Category to CategoryWithProductsResponse: {}", category);

        Set<Product> products = category.getProducts();

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


}
