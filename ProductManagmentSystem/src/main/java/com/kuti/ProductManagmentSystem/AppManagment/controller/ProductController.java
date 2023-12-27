package com.kuti.ProductManagmentSystem.AppManagment.controller;

import com.kuti.ProductManagmentSystem.AppManagment.dto.requestDto.CreateProductRequest;
import com.kuti.ProductManagmentSystem.AppManagment.dto.responseDto.ProductResponse;
import com.kuti.ProductManagmentSystem.AppManagment.service.ProductServiceImpl;
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
    private final ProductServiceImpl productService;

    private final Logger logger = LoggerFactory.getLogger(ProductController.class);

    public ProductController(ProductServiceImpl productService) {
        this.productService = productService;
    }

    @PostMapping("/addProduct")
    public ResponseEntity<ProductResponse> createProduct(@ModelAttribute("product") CreateProductRequest createProductRequest,
                                                         @RequestParam("imageFile") MultipartFile imageFile) throws IOException {
        logger.info("Create Product :"+createProductRequest);

        if (createProductRequest == null || imageFile == null) {
            return ResponseEntity.badRequest().body(ProductResponse.builder()
                    .message("Invalid input. Product could not be created.")
                    .build());
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

    @GetMapping("/delete/{productId}")
    public ResponseEntity<ProductResponse> deleteProduct(@PathVariable long productId) {
        ProductResponse productResponse=productService.deleteProduct(productId);
        return ResponseEntity.ok(productResponse);
    }

    @GetMapping("/deleteProductImage/{productId}")
    public ResponseEntity<?> deleteProductImage(@PathVariable long productId){
        
    }
}
