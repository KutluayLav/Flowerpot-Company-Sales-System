package com.kuti.ProductManagmentSystem.AppManagment.service;
import com.kuti.ProductManagmentSystem.AppManagment.dto.requestDto.CreateProductRequest;
import com.kuti.ProductManagmentSystem.AppManagment.dto.requestDto.UpdateProductRequest;
import com.kuti.ProductManagmentSystem.AppManagment.dto.responseDto.ProductResponse;
import com.kuti.ProductManagmentSystem.AppManagment.exception.ProductNotFoundException;
import com.kuti.ProductManagmentSystem.AppManagment.mapper.ProductMapper;
import com.kuti.ProductManagmentSystem.AppManagment.model.Category;
import com.kuti.ProductManagmentSystem.AppManagment.model.FileData;
import com.kuti.ProductManagmentSystem.AppManagment.model.Product;
import com.kuti.ProductManagmentSystem.AppManagment.repository.CategoryRepository;
import com.kuti.ProductManagmentSystem.AppManagment.repository.ProductRepository;
import com.kuti.ProductManagmentSystem.AppManagment.service.impl.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final FileDataService fileDataService;
    private final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository, FileDataService fileDataService) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.fileDataService = fileDataService;
    }

    @Override
    public ProductResponse saveProduct(CreateProductRequest createProductRequest, MultipartFile multipartFile)
            throws IOException {

        validateCreateProductRequest(createProductRequest);

        if (productRepository.existsByProductName(createProductRequest.getProductName())) {
            throw new IllegalArgumentException("Product with the same name already exists.");
        }

        FileData uploadImage = null;

        if (multipartFile != null && !multipartFile.isEmpty()) {
            uploadImage = fileDataService.uploadImageToFileSystem(multipartFile);
        }

        Product product = Product.builder()
                .productName(createProductRequest.getProductName())
                .description(createProductRequest.getDescription())
                .price(createProductRequest.getPrice())
                .features(createProductRequest.getFeatures())
                .quantity(createProductRequest.getQuantity())
                .fileData(uploadImage)
                .status(true)
                .build();

        if (createProductRequest.getCategoryName() != null && !createProductRequest.getCategoryName().isEmpty()) {

            Category existingCategory = categoryRepository.findByName(createProductRequest.getCategoryName())
                    .orElseThrow(() -> new IllegalArgumentException
                            ("Category with Name " + createProductRequest.getCategoryName() + " not found."));

            product.setCategory(existingCategory);
        }

        Product savedProduct = productRepository.save(product);

        ProductResponse productResponse =ProductMapper.mapToProductResponseCreated(savedProduct);

        return productResponse;
    }

    @Override
    public List<ProductResponse> getAllProduct() {
        List<Product> allProducts = productRepository.findAll();

        if (!allProducts.isEmpty()) {
            return ProductMapper.mapToProductResponseList(allProducts);
        }
        return Collections.emptyList();
    }

    @Override
    public ProductResponse getProductById(long id) {
        Optional<Product> product = productRepository.findById(id);

        if (product.isPresent()){
            ProductResponse productResponse=ProductMapper.mapToProductResponse(product.get());
            return productResponse;
        }

        return null;
    }

    @Override
    public ProductResponse deleteProduct(Long productId)  {
        Product existingProduct = productRepository.findById(productId).orElse(null);

        ProductResponse productResponse=ProductResponse.builder()
                .message("Product Deleted Successfully")
                .build();
        try {
            fileDataService.deleteImage(existingProduct.getFileData().getId());
        } catch (IOException e) {
            throw new RuntimeException("Image cannot be deleted!!!"+e);
        }

        if (existingProduct != null) {
            productRepository.delete(existingProduct);
            return productResponse;
        } else {
            throw new ProductNotFoundException("Product not exist"+productId);
        }
    }

    @Override
    public ProductResponse deleteImage(Long productId) {
        Product existingProduct = productRepository.findById(productId).orElse(null);

        ProductResponse productResponse=ProductResponse.builder()
                .name(existingProduct.getProductName())
                .quantity(existingProduct.getQuantity())
                .price(existingProduct.getPrice())
                .description(existingProduct.getDescription())
                .message("Product Image Deleted Successfully")
                .build();

        if(existingProduct.getFileData() !=null){
            try {
                fileDataService.deleteImage(existingProduct.getFileData().getId());
                logger.info(existingProduct.getFileData().getName());
            } catch (IOException e) {
                throw new RuntimeException("Image cannot be deleted!!!"+e);
            }
            existingProduct.setFileData(null);
            productRepository.save(existingProduct);
        }
        return productResponse;
    }

    @Override
    public ProductResponse editProduct(UpdateProductRequest updateProductRequest, Long id, MultipartFile file) {
        Product existingProduct = productRepository.findById(id).orElse(null);

        if (existingProduct == null) {
            throw new ProductNotFoundException("Product not exist: " + id);
        }

        if (file != null && !file.isEmpty()) {
            try {
                fileDataService.deleteImage(existingProduct.getFileData().getId());
                FileData newImage = fileDataService.uploadImageToFileSystem(file);
                existingProduct.setFileData(newImage);
            } catch (IOException e) {
                throw new RuntimeException("Image cannot be updated: " + e.getMessage());
            }
        }
        if (updateProductRequest.getProductName() != null) {
            existingProduct.setProductName(updateProductRequest.getProductName());
        }
        if (updateProductRequest.getDescription() != null) {
            existingProduct.setDescription(updateProductRequest.getDescription());
        }
        if (updateProductRequest.getPrice() != null) {
            existingProduct.setPrice(updateProductRequest.getPrice());
        }
        if (updateProductRequest.getFeatures() != null) {
            existingProduct.setFeatures(updateProductRequest.getFeatures());
        }
        if (updateProductRequest.getQuantity() != null) {
            existingProduct.setQuantity(updateProductRequest.getQuantity());
        }
        productRepository.save(existingProduct);

        return ProductResponse.builder()
                .id(existingProduct.getId())
                .name(existingProduct.getProductName())
                .quantity(existingProduct.getQuantity())
                .price(existingProduct.getPrice())
                .description(existingProduct.getDescription())
                .fileData(existingProduct.getFileData())
                .message("Product Updated Successfully")
                .build();
    }

    private void validateCreateProductRequest(CreateProductRequest createProductRequest) {
        if (createProductRequest.getProductName() == null || createProductRequest.getProductName().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be null or empty.");
        }
    }
}
