package com.kuti.ProductManagmentSystem.AppManagment.exception;

import java.io.IOException;

public class FileUploadException extends RuntimeException{

    public FileUploadException(String message, IOException e) {
        super(message);
    }
}
