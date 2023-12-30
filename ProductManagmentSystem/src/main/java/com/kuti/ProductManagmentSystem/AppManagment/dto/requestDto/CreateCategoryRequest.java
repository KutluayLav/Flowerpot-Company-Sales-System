package com.kuti.ProductManagmentSystem.AppManagment.dto.requestDto;

import com.kuti.ProductManagmentSystem.AppManagment.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCategoryRequest {

    private String name;

    private List<Long> productIds;

}
