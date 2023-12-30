package com.kuti.ProductManagmentSystem.AppManagment.service.impl;

import com.kuti.ProductManagmentSystem.AppManagment.dto.requestDto.CreateCategoryRequest;
import com.kuti.ProductManagmentSystem.AppManagment.dto.responseDto.CategoryResponse;

public interface CategoryService {

    public CategoryResponse addCategory(CreateCategoryRequest createCategoryRequest);

}
