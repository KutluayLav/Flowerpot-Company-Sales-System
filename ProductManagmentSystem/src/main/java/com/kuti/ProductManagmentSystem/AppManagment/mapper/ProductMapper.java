package com.kuti.ProductManagmentSystem.AppManagment.mapper;
import com.kuti.ProductManagmentSystem.AppManagment.dto.responseDto.ProductNameResponse;
import com.kuti.ProductManagmentSystem.AppManagment.dto.responseDto.ProductResponse;
import com.kuti.ProductManagmentSystem.AppManagment.dto.responseDto.ProductWithCategoryResponse;
import com.kuti.ProductManagmentSystem.AppManagment.model.Category;
import com.kuti.ProductManagmentSystem.AppManagment.model.Product;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductMapper {

    public static ProductResponse mapToProductResponse(Product product) {
        if (product.getCategory() == null) {
            return ProductResponse.builder()
                    .id(product.getId())
                    .name(product.getProductName())
                    .price(product.getPrice())
                    .quantity(product.getQuantity())
                    .description(product.getDescription())
                    .fileData(product.getFileData())
                    .message("Ürün Gösterildi:" + product.getProductName())
                    .build();
        } else {
            return ProductResponse.builder()
                    .id(product.getId())
                    .name(product.getProductName())
                    .price(product.getPrice())
                    .quantity(product.getQuantity())
                    .description(product.getDescription())
                    .category(mapToProductWithCategoryResponse(product.getCategory()))
                    .fileData(product.getFileData())
                    .message("Ürün Gösterildi:" + product.getProductName())
                    .build();
        }
    }

    public static ProductResponse mapToProductResponseCreated(Product product) {
        if (product.getCategory() == null){
            return ProductResponse.builder()
                    .id(product.getId())
                    .name(product.getProductName())
                    .price(product.getPrice())
                    .quantity(product.getQuantity())
                    .description(product.getDescription())
                    .fileData(product.getFileData())
                    .message("Product Created Success:"+product.getProductName())
                    .build();
        }else {
            return ProductResponse.builder()
                    .id(product.getId())
                    .name(product.getProductName())
                    .price(product.getPrice())
                    .quantity(product.getQuantity())
                    .description(product.getDescription())
                    .fileData(product.getFileData())
                    .category(mapToProductWithCategoryResponse(product.getCategory()))
                    .message("Product Created Success:"+product.getProductName())
                    .build();
        }

    }

    public static List<ProductNameResponse> mapToProductNameResponseList(List<Product> products){
          return products.stream()
                .map(ProductMapper::mapToProductNameResponse)
                .collect(Collectors.toList());
    }
    public static List<ProductResponse> mapToProductResponseList(List<Product> products) {
        return products.stream()
                .map(ProductMapper::mapToProductResponse)
                .collect(Collectors.toList());
    }

    private static ProductWithCategoryResponse mapToProductWithCategoryResponse(Category category){
        return ProductWithCategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .build();
    }

    private static ProductNameResponse mapToProductNameResponse(Product product){
        return ProductNameResponse.builder()
                .name(product.getProductName())
                .description(product.getDescription())
                .id(product.getId())
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .build();
    }



}
