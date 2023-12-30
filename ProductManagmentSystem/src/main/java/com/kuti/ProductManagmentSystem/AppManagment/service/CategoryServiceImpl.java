package com.kuti.ProductManagmentSystem.AppManagment.service;

import com.kuti.ProductManagmentSystem.AppManagment.dto.requestDto.CreateCategoryRequest;
import com.kuti.ProductManagmentSystem.AppManagment.dto.responseDto.CategoryNameResponse;
import com.kuti.ProductManagmentSystem.AppManagment.dto.responseDto.CategoryResponse;
import com.kuti.ProductManagmentSystem.AppManagment.dto.responseDto.CategoryWithProductsResponse;
import com.kuti.ProductManagmentSystem.AppManagment.mapper.CategoryMapper;
import com.kuti.ProductManagmentSystem.AppManagment.model.Category;
import com.kuti.ProductManagmentSystem.AppManagment.model.Product;
import com.kuti.ProductManagmentSystem.AppManagment.repository.CategoryRepository;
import com.kuti.ProductManagmentSystem.AppManagment.repository.ProductRepository;
import com.kuti.ProductManagmentSystem.AppManagment.service.impl.CategoryService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private final ProductRepository productRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    @Override
    public CategoryResponse addCategory(CreateCategoryRequest createCategoryRequest) {
        validateCreateCategoryRequest(createCategoryRequest);

        if (categoryRepository.existsCategoryByName(createCategoryRequest.getName())) {
            throw new IllegalArgumentException("Category with the same name already exists.");
        }

        Category category = Category.builder()
                .name(createCategoryRequest.getName())
                .description(createCategoryRequest.getDescription())
                .build();

        List<Long> productIds = createCategoryRequest.getProductIds();

        if (productIds != null && !productIds.isEmpty()) {
            for (Long productId : productIds) {
                Product existingProduct = productRepository.findById(productId)
                        .orElseThrow(() -> new IllegalArgumentException("Product with ID " + productId + " not found."));
                existingProduct.setCategory(category);
                productRepository.save(existingProduct);
            }
        }

        Category savedCategory = categoryRepository.save(category);
        return CategoryMapper.mapToCategoryResponse(savedCategory);
    }

    @Override
    public CategoryNameResponse getAllCategories() {

        List<Category> categories =categoryRepository.findAll();

        if (!categories.isEmpty()) {
            List<String> categoryNames = categories.stream()
                    .map(Category::getName)
                    .collect(Collectors.toList());

            List<Long> categoryIds =categories.stream()
                    .map(Category::getId)
                    .collect(Collectors.toList());

            return CategoryNameResponse.builder()
                    .names(categoryNames)
                    .id(categoryIds)
                    .message("Category Showing :"+categoryNames)
                    .build();
        }
        return CategoryNameResponse.builder()
                .message("Category Not Found in DataBase")
                .build();
    }

    @Override
    public List<CategoryWithProductsResponse> getAllCategoriesWithProducts() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(CategoryMapper::mapToCategoryWithProductsResponse)
                .collect(Collectors.toList());
    }

    private void validateCreateCategoryRequest(CreateCategoryRequest createCategoryRequest) {

        if (createCategoryRequest.getName() == null || createCategoryRequest.getName().isEmpty()) {
            throw new IllegalArgumentException("Category name cannot be null or empty.");
        }
    }

}
