package com.kuti.ProductManagmentSystem.AppManagment.dto.responseDto;

import com.kuti.ProductManagmentSystem.AppManagment.model.Category;
import com.kuti.ProductManagmentSystem.AppManagment.model.FileData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductCategoryNameResponse {

    private Long id;

    private String message;

    private String name;

    private String description;

    private BigDecimal price;

    private Long quantity;

    private FileData fileData;

    private String category;

}
