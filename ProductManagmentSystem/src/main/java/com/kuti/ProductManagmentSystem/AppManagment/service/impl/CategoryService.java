package com.kuti.ProductManagmentSystem.AppManagment.service.impl;

import com.kuti.ProductManagmentSystem.AppManagment.dto.requestDto.CreateCategoryRequest;
import com.kuti.ProductManagmentSystem.AppManagment.dto.responseDto.CategoryNameResponse;
import com.kuti.ProductManagmentSystem.AppManagment.dto.responseDto.CategoryResponse;
import com.kuti.ProductManagmentSystem.AppManagment.dto.responseDto.CategoryWithProductsResponse;

import java.util.List;

public interface CategoryService {

    public CategoryResponse addCategory(CreateCategoryRequest createCategoryRequest);

    public List<CategoryNameResponse> getAllCategories();

    public List<CategoryWithProductsResponse> getAllCategoriesWithProducts();

    public CategoryResponse getCategoryById(long id);

}
