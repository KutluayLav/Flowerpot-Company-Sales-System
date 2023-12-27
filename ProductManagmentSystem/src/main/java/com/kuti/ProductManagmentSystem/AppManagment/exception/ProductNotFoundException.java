package com.kuti.ProductManagmentSystem.AppManagment.exception;

public class ProductNotFoundException extends RuntimeException{
    public ProductNotFoundException(String message) {
        super(message);
    }
}
