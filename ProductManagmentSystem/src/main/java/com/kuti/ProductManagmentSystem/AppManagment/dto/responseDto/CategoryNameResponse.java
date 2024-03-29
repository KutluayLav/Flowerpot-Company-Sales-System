package com.kuti.ProductManagmentSystem.AppManagment.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryNameResponse {

    private Long id;

    private String categoryName;

    private String categoryDescription;

    private String message;
}
