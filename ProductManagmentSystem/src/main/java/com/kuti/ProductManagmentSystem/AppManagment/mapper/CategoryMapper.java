package com.kuti.ProductManagmentSystem.AppManagment.mapper;

import com.kuti.ProductManagmentSystem.AppManagment.dto.responseDto.CategoryResponse;
import com.kuti.ProductManagmentSystem.AppManagment.model.Category;
public class CategoryMapper {

    public static CategoryResponse mapToCategoryResponse(Category category){
        return CategoryResponse.builder()
                .id(category.getId())
                .productList(category.getProducts())
                .name(category.getName())
                .message("Category:"+category.getName()+" Displayed")
                .build();
    }


}
