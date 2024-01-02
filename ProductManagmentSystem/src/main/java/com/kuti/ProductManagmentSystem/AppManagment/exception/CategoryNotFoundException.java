package com.kuti.ProductManagmentSystem.AppManagment.exception;


public class CategoryNotFoundException extends RuntimeException{

    public CategoryNotFoundException(String message) {
        super(message);
    }
}
