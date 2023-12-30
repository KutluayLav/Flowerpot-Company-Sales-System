package com.kuti.ProductManagmentSystem.AppManagment.service;

import com.kuti.ProductManagmentSystem.AppManagment.dto.requestDto.CreateCategoryRequest;
import com.kuti.ProductManagmentSystem.AppManagment.dto.responseDto.CategoryResponse;
import com.kuti.ProductManagmentSystem.AppManagment.mapper.CategoryMapper;
import com.kuti.ProductManagmentSystem.AppManagment.model.Category;
import com.kuti.ProductManagmentSystem.AppManagment.model.Product;
import com.kuti.ProductManagmentSystem.AppManagment.repository.CategoryRepository;
import com.kuti.ProductManagmentSystem.AppManagment.repository.ProductRepository;
import com.kuti.ProductManagmentSystem.AppManagment.service.impl.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

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

    private void validateCreateCategoryRequest(CreateCategoryRequest createCategoryRequest) {

        if (createCategoryRequest.getName() == null || createCategoryRequest.getName().isEmpty()) {
            throw new IllegalArgumentException("Category name cannot be null or empty.");
        }
    }

}
