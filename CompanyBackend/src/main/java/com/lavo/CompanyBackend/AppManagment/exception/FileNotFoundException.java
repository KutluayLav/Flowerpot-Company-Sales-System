package com.lavo.CompanyBackend.AppManagment.exception;

import java.io.IOException;

public class FileNotFoundException extends IOException {
    public FileNotFoundException(String message) {
        super(message);
    }
}
