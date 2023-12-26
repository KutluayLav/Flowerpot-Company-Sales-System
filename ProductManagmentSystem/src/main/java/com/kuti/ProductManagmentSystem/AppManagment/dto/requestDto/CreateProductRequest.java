package com.kuti.ProductManagmentSystem.AppManagment.dto.requestDto;

import com.kuti.ProductManagmentSystem.AppManagment.model.Category;
import com.kuti.ProductManagmentSystem.AppManagment.model.FileData;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateProductRequest {

    @Valid
    @NotNull(message = "Product Name Cannot be Empty!!!")
    private String productName;

    private String description;

    private String features;

    private BigDecimal price;

    private long quantity;

    private FileData fileData;

    private Category category;

}
