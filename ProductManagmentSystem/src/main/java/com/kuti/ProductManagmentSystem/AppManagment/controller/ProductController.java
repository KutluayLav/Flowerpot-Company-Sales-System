package com.kuti.ProductManagmentSystem.AppManagment.controller;

import com.kuti.ProductManagmentSystem.AppManagment.dto.requestDto.CreateProductRequest;
import com.kuti.ProductManagmentSystem.AppManagment.dto.responseDto.ProductResponse;
import com.kuti.ProductManagmentSystem.AppManagment.service.impl.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/product/v1")
@CrossOrigin(origins = "http://localhost:3000")
public class ProductController {


    private final ProductService productService;

    private final Logger logger = LoggerFactory.getLogger(ProductController.class);

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/addProduct")
    public ResponseEntity<ProductResponse> createProduct(@RequestBody CreateProductRequest createProductRequest,
                                                         @RequestPart("imageFile") MultipartFile imageFile) throws IOException {

        logger.info("Create Product :"+createProductRequest);

        if (createProductRequest == null || imageFile == null) {

            ProductResponse productResponse = ProductResponse.builder()
                    .name(createProductRequest.getProductName())
                    .price(createProductRequest.getPrice())
                    .quantity(createProductRequest.getQuantity())
                    .description(createProductRequest.getDescription())
                    .message("product could not be created")
                    .build();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(productResponse);
        }
        productService.saveProduct(createProductRequest,imageFile);

        ProductResponse productResponse = ProductResponse.builder()
                .name(createProductRequest.getProductName())
                .price(createProductRequest.getPrice())
                .quantity(createProductRequest.getQuantity())
                .description(createProductRequest.getDescription())
                .message("Product Created Success")
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(productResponse);
    }

}