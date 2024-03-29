package com.kuti.ProductManagmentSystem.AppManagment.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductNameResponse {

    private Long id;

    private String name;

    private String description;

    private BigDecimal price;

    private Long quantity;
}
