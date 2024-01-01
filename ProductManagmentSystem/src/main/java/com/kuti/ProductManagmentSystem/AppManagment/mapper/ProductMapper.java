package com.kuti.ProductManagmentSystem.AppManagment.mapper;

import com.kuti.ProductManagmentSystem.AppManagment.dto.responseDto.ProductResponse;
import com.kuti.ProductManagmentSystem.AppManagment.model.Product;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductMapper {

    public static ProductResponse mapToProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getProductName())
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .description(product.getDescription())
                .fileData(product.getFileData())
                .message("Product Displayed:"+product.getProductName())
                .build();
    }

    public static ProductResponse mapToProductResponseCreated(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getProductName())
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .description(product.getDescription())
                .fileData(product.getFileData())
                .category(product.getCategory())
                .message("Product Created Success:"+product.getProductName())
                .build();
    }

    public static List<ProductResponse> mapToProductResponseList(List<Product> products) {
        return products.stream()
                .map(ProductMapper::mapToProductResponse)
                .collect(Collectors.toList());
    }

}
