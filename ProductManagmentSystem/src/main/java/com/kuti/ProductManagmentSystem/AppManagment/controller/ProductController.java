package com.kuti.ProductManagmentSystem.AppManagment.controller;

import com.kuti.ProductManagmentSystem.AppManagment.dto.requestDto.CreateProductRequest;
import com.kuti.ProductManagmentSystem.AppManagment.dto.requestDto.UpdateProductRequest;
import com.kuti.ProductManagmentSystem.AppManagment.dto.responseDto.ProductResponse;
import com.kuti.ProductManagmentSystem.AppManagment.exception.ProductNotFoundException;
import com.kuti.ProductManagmentSystem.AppManagment.service.ProductServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

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
    public ResponseEntity<ProductResponse> createProduct(
            @ModelAttribute("product") CreateProductRequest createProductRequest,
            @RequestParam(value = "imageFile",required = false)
            MultipartFile imageFile) throws IOException {

        logger.info("Create Product :"+createProductRequest);

        if (createProductRequest == null ) {
            return ResponseEntity.badRequest().body(ProductResponse.builder()
                    .message("Invalid input. Product could not be created.")
                    .build());
        }

        ProductResponse productResponse= productService.saveProduct(createProductRequest,imageFile);

        return ResponseEntity.status(HttpStatus.CREATED).body(productResponse);
    }

    @GetMapping("/delete/{productId}")
    public ResponseEntity<ProductResponse> deleteProduct(@PathVariable long productId) {
        ProductResponse productResponse=productService.deleteProduct(productId);
        return ResponseEntity.ok(productResponse);
    }

    @GetMapping("/deleteProductImage/{productId}")
    public ResponseEntity<ProductResponse> deleteProductImage(@PathVariable long productId){
        ProductResponse productResponse=productService.deleteImage(productId);
        return ResponseEntity.ok(productResponse);
    }

    @GetMapping("/getProductById/{productId}")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable long productId){

        ProductResponse productResponse =productService.getProductById(productId);

        if (productResponse != null){
            return ResponseEntity.ok(productResponse);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }


    @GetMapping("/getAllProducts")
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        logger.info("Get All Products");

        List<ProductResponse> allProducts = productService.getAllProduct();

        if (allProducts.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(allProducts);
    }

    // i will fix that code for image
    @PostMapping("/updateProduct/{productId}")
    public ResponseEntity<ProductResponse> updateProduct(@RequestBody UpdateProductRequest updateProductRequest,
                                                         @PathVariable long productId,
                                                         @RequestParam(value = "imageFile", required = false)
                                                             MultipartFile imageFile){
        logger.info("Update Product with ID {}: {}", productId, updateProductRequest);

        try {
            ProductResponse response = productService.editProduct(updateProductRequest, productId, imageFile);
            return ResponseEntity.ok(response);
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    ProductResponse.builder()
                            .message("Product not found with ID: " + productId)
                            .build()
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    ProductResponse.builder()
                            .message("Error updating product: " + e.getMessage())
                            .build()
            );
        }
    }



}
