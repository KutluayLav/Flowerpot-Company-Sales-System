package com.kuti.ProductManagmentSystem.AppManagment.dto.responseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryWithProductsResponse {

    private Long id;

    private String name;

    private String description;

    private Set<ProductResponse> productList;

}
