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
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private final ProductRepository productRepository;

    private final Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

    public CategoryServiceImpl(CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    @Override
    public CategoryResponse addCategory(CreateCategoryRequest createCategoryRequest) {
        validateCreateCategoryRequest(createCategoryRequest);

        if (categoryRepository.existsCategoryByName(createCategoryRequest.getName())) {
            throw new IllegalArgumentException("Aynı isimde bir kategori zaten mevcut.");
        }

        Category category = Category.builder()
                .name(createCategoryRequest.getName())
                .description(createCategoryRequest.getDescription())
                .build();

        List<Long> productIds = createCategoryRequest.getProductIds();

        if (productIds != null && !productIds.isEmpty()) {
            List<Product> products = productRepository.findAllById(productIds);

            if (products.size() != productIds.size()) {
                throw new IllegalArgumentException("Sağlanan ID'ye sahip bir veya daha fazla ürün bulunamadı.");
            }

            for (Product product : products) {
                if (product.getCategory() != null) {
                    throw new IllegalArgumentException("Ürün zaten bir kategoriye atanmış: " + product.getProductName());
                }
                product.setCategory(category);
            }

            category.setProducts(new ArrayList<>(products));
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
    @Transactional
    public List<CategoryWithProductsResponse> getAllCategoriesWithProducts() {
        List<Category> categories = categoryRepository.findAll();

        logger.info("Categories:" + categories);

        return categories.stream()
                .peek(category -> Hibernate.initialize(category.getProducts()))
                .map(CategoryMapper::mapToCategoryWithProductsResponse)
                .collect(Collectors.toList());
    }

    private void validateCreateCategoryRequest(CreateCategoryRequest createCategoryRequest) {

        if (createCategoryRequest.getName() == null || createCategoryRequest.getName().isEmpty()) {
            throw new IllegalArgumentException("Category name cannot be null or empty.");
        }
    }

}
