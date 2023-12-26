package com.lavo.CompanyBackend.AppManagment.service;

import com.lavo.CompanyBackend.AppManagment.dto.requestDto.CreateProductRequest;
import com.lavo.CompanyBackend.AppManagment.dto.requestDto.UpdateProductRequest;
import com.lavo.CompanyBackend.AppManagment.model.Product;
import com.lavo.CompanyBackend.AppManagment.repository.ProductRepository;
import com.lavo.CompanyBackend.AppManagment.service.impl.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
    public Product saveProduct(CreateProductRequest createProductRequest, MultipartFile multipartFile) {
        return null;
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
    public String deleteProduct(Long id) {
        return null;
    }

    @Override
    public Product editProduct(UpdateProductRequest updateProductRequest, Long id) {
        return null;
    }
}
