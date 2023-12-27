package com.kuti.ProductManagmentSystem.AppManagment.service;

import com.kuti.ProductManagmentSystem.AppManagment.dto.requestDto.CreateProductRequest;
import com.kuti.ProductManagmentSystem.AppManagment.dto.requestDto.UpdateProductRequest;
import com.kuti.ProductManagmentSystem.AppManagment.dto.responseDto.ProductResponse;
import com.kuti.ProductManagmentSystem.AppManagment.exception.ProductNotFoundException;
import com.kuti.ProductManagmentSystem.AppManagment.model.FileData;
import com.kuti.ProductManagmentSystem.AppManagment.model.Product;
import com.kuti.ProductManagmentSystem.AppManagment.repository.ProductRepository;
import com.kuti.ProductManagmentSystem.AppManagment.service.impl.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final FileDataService fileDataService;

    public ProductServiceImpl(ProductRepository productRepository, FileDataService fileDataService) {
        this.productRepository = productRepository;
        this.fileDataService = fileDataService;
    }

    @Override
    public Product saveProduct(CreateProductRequest createProductRequest, MultipartFile multipartFile) throws IOException {

        FileData uploadImage = fileDataService.uploadImageToFileSystem(multipartFile);

        createProductRequest.setFileData(uploadImage);

        Product product=Product.builder()
                .productName(createProductRequest.getProductName())
                .description(createProductRequest.getDescription())
                .price(createProductRequest.getPrice())
                .features(createProductRequest.getFeatures())
                .category(createProductRequest.getCategory())
                .quantity(createProductRequest.getQuantity())
                .fileData(createProductRequest.getFileData())
                .status(true)
                .build();

        return productRepository.save(product);
    }

    @Override
    public List<Product> getAllProduct() {
        return null;
    }

    @Override
    public Product getProductById(Long id) {
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
    public Product editProduct(UpdateProductRequest updateProductRequest, Long id) {
        return null;
    }
}
