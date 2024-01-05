package com.kuti.ProductManagmentSystem.AppManagment.dto.requestDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddProductToCategoryRequest {

    private Long categoryId;

    private List<Long> productIds;

}
