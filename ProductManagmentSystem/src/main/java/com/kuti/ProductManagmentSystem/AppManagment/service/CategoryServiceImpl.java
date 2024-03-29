package com.kuti.ProductManagmentSystem.AppManagment.service;
import com.kuti.ProductManagmentSystem.AppManagment.dto.requestDto.AddProductToCategoryRequest;
import com.kuti.ProductManagmentSystem.AppManagment.dto.requestDto.CreateCategoryRequest;
import com.kuti.ProductManagmentSystem.AppManagment.dto.responseDto.CategoryNameResponse;
import com.kuti.ProductManagmentSystem.AppManagment.dto.responseDto.CategoryResponse;
import com.kuti.ProductManagmentSystem.AppManagment.dto.responseDto.CategoryWithProductsResponse;
import com.kuti.ProductManagmentSystem.AppManagment.exception.CategoryNotFoundException;
import com.kuti.ProductManagmentSystem.AppManagment.mapper.CategoryMapper;
import com.kuti.ProductManagmentSystem.AppManagment.model.Category;
import com.kuti.ProductManagmentSystem.AppManagment.model.Product;
import com.kuti.ProductManagmentSystem.AppManagment.repository.CategoryRepository;
import com.kuti.ProductManagmentSystem.AppManagment.repository.ProductRepository;
import com.kuti.ProductManagmentSystem.AppManagment.service.impl.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.kuti.ProductManagmentSystem.AppManagment.mapper.CategoryMapper.mapToCategoryNameResponses;
import static com.kuti.ProductManagmentSystem.AppManagment.mapper.CategoryMapper.mapToCategoryResponse;


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

        logger.info("Gelen createCategoryRequest isim :{}",createCategoryRequest.getName());

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
    public CategoryResponse addProductToCategory(AddProductToCategoryRequest addProductToCategoryRequest) {
        validateAddProductToCategory(addProductToCategoryRequest);

        var categoryId = addProductToCategoryRequest.getCategoryId();

        Category category = getCategoryByCategoryId(categoryId);

        List<Long> productIds = addProductToCategoryRequest.getProductIds();

        if (productIds != null && !productIds.isEmpty()) {
            List<Product> products = productRepository.findAllById(productIds);

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

    @Override // categories
    public List<CategoryNameResponse> getAllCategories() {
        List<Category> categories =categoryRepository.findAll();

        List<CategoryNameResponse> categoryNameResponses;

        if (!categories.isEmpty()){
            categoryNameResponses=mapToCategoryNameResponses(categories);
        }else {
            throw new CategoryNotFoundException("Category Does Not Exist");
        }

        return categoryNameResponses;
    }
    @Override
    public List<CategoryWithProductsResponse> getAllCategoriesWithProducts() {

        List<Category> categories = categoryRepository.findAll();

        if (!categories.isEmpty()) {
            return CategoryMapper.mapToCategoryWithProductsNameResponse(categories);
        }else {
            throw new CategoryNotFoundException("Category Does Not Exist");
        }
    }

    @Override
    public CategoryResponse getCategoryById(long id) {

        Optional<Category> category = categoryRepository.findById(id);

        if (!category.isEmpty()){
            CategoryResponse categoryResponse =mapToCategoryResponse(category.get());
            return categoryResponse;
        }

        return null;
    }

    private Category getCategoryByCategoryId(long id){

        Optional<Category> optionalCategory = categoryRepository.findById(id);

        if (!optionalCategory.isEmpty()){
            Category category =optionalCategory.get();
            return category;
        }
        return null;
    }

    private void validateAddProductToCategory(AddProductToCategoryRequest addProductToCategoryRequest){

        if (addProductToCategoryRequest.getCategoryId() == null || addProductToCategoryRequest.getProductIds() == null){
            throw new IllegalArgumentException("Category Id or Product Ids cannot be null or Empty");
        }

    }

    //Kutluay ulutas
    private void validateCreateCategoryRequest(CreateCategoryRequest createCategoryRequest) {

        if (createCategoryRequest.getName() == null || createCategoryRequest.getName().isEmpty()) {
            throw new IllegalArgumentException("Category name cannot be null or empty.");
        }
    }

}
