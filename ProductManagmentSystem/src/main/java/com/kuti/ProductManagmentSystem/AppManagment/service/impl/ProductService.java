package com.kuti.ProductManagmentSystem.AppManagment.service.impl;

import com.kuti.ProductManagmentSystem.AppManagment.dto.requestDto.CreateProductRequest;
import com.kuti.ProductManagmentSystem.AppManagment.dto.requestDto.UpdateProductRequest;
import com.kuti.ProductManagmentSystem.AppManagment.model.Product;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {

    public Product saveProduct(CreateProductRequest createProductRequest, MultipartFile multipartFile) throws IOException;

    public List<Product> getAllProduct();

    public Product getProductById(Long id);

    public String deleteProduct(Long id);

    public Product editProduct(UpdateProductRequest updateProductRequest, Long id);
}
