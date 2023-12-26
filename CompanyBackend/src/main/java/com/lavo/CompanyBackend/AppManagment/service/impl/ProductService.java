package com.lavo.CompanyBackend.AppManagment.service.impl;

import com.lavo.CompanyBackend.AppManagment.dto.requestDto.CreateProductRequest;
import com.lavo.CompanyBackend.AppManagment.dto.requestDto.UpdateProductRequest;
import com.lavo.CompanyBackend.AppManagment.model.Product;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {

    public Product saveProduct(CreateProductRequest createProductRequest, MultipartFile multipartFile);

    public List<Product> getAllProduct();

    public Product getProductById(Long id);

    public String deleteProduct(Long id);

    public Product editProduct(UpdateProductRequest updateProductRequest, Long id);
}
