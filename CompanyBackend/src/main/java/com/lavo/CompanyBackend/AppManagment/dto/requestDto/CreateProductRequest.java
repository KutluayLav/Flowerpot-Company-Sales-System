package com.lavo.CompanyBackend.AppManagment.dto.requestDto;

import com.lavo.CompanyBackend.AppManagment.model.Category;
import com.lavo.CompanyBackend.AppManagment.model.FileData;
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

    @NotNull(message = "Product Name Cannot be Empty!!!")
    private String productName;

    private String description;

    private String features;

    private BigDecimal price;

    private long quantity;

    private FileData fileData;

    private Category category;

}
