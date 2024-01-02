package com.kuti.ProductManagmentSystem.AppManagment.controller;

import com.kuti.ProductManagmentSystem.AppManagment.dto.requestDto.CreateCategoryRequest;
import com.kuti.ProductManagmentSystem.AppManagment.dto.responseDto.CategoryNameResponse;
import com.kuti.ProductManagmentSystem.AppManagment.dto.responseDto.CategoryResponse;
import com.kuti.ProductManagmentSystem.AppManagment.dto.responseDto.CategoryWithProductsResponse;
import com.kuti.ProductManagmentSystem.AppManagment.service.CategoryServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category/v1")
@CrossOrigin(origins = "http://localhost:3000")
public class CategoryController {

    private final CategoryServiceImpl categoryService;
    private final Logger logger = LoggerFactory.getLogger(CategoryController.class);

    public CategoryController(CategoryServiceImpl categoryService) {
        this.categoryService = categoryService;
    }


    @PostMapping("/addCategory")
    public ResponseEntity<CategoryResponse> addCategory(@RequestBody CreateCategoryRequest createCategoryRequest){

        logger.info("Added Category:"+createCategoryRequest.getName());
        logger.info("Category Product Ids List:"+createCategoryRequest.getProductIds());

        try {
            CategoryResponse categoryResponse=categoryService.addCategory(createCategoryRequest);

            return ResponseEntity.status(HttpStatus.CREATED).body(categoryResponse);

        }catch (IllegalArgumentException e) {

            logger.error("Error adding category: {}", e.getMessage());

            CategoryResponse badResponse=CategoryResponse.builder()
                    .message("Category Created Failed !!!!")
                    .build();

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(badResponse);

        } catch (Exception e) {

            logger.error("Unexpected error adding category", e);

            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/getAllCategories")
    public ResponseEntity<List<CategoryNameResponse>> getAllCategories(){

        List<CategoryNameResponse> categoryNameResponse=categoryService.getAllCategories();

        return ResponseEntity.ok(categoryNameResponse);
    }

    @GetMapping("/getAllCategoriesAndProducts")
    public ResponseEntity<List<CategoryWithProductsResponse>> getAllCategoriesAndProducts() {
        List<CategoryWithProductsResponse> response = categoryService.getAllCategoriesWithProducts();
        return ResponseEntity.ok(response);
    }



}
