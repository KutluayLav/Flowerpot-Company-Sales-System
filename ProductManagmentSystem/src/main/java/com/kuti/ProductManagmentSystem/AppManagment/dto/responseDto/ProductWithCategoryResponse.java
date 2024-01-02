package com.kuti.ProductManagmentSystem.AppManagment.dto.responseDto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductWithCategoryResponse {

    private String name;

    private String description;

    private Long id;

}
