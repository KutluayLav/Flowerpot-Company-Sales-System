package com.kuti.ProductManagmentSystem.AppManagment.service.impl;

import com.kuti.ProductManagmentSystem.AppManagment.dto.requestDto.CreateProductRequest;
import com.kuti.ProductManagmentSystem.AppManagment.dto.requestDto.UpdateProductRequest;
import com.kuti.ProductManagmentSystem.AppManagment.dto.responseDto.ProductResponse;
import com.kuti.ProductManagmentSystem.AppManagment.model.Product;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {

    public ProductResponse saveProduct(CreateProductRequest createProductRequest, MultipartFile multipartFile) throws IOException;

    public List<ProductResponse> getAllProduct();

    public ProductResponse getProductById(long id);

    public ProductResponse deleteProduct(Long id);

    public ProductResponse deleteImage(Long id);
    public ProductResponse editProduct(UpdateProductRequest updateProductRequest, Long id,MultipartFile file);
}
