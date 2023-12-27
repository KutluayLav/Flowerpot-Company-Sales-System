package com.kuti.ProductManagmentSystem.AppManagment.service;

import com.kuti.ProductManagmentSystem.AppManagment.controller.ProductController;
import com.kuti.ProductManagmentSystem.AppManagment.exception.FileNotFoundException;
import com.kuti.ProductManagmentSystem.AppManagment.exception.FileUploadException;
import com.kuti.ProductManagmentSystem.AppManagment.model.FileData;
import com.kuti.ProductManagmentSystem.AppManagment.repository.FileDataRepository;
import lombok.extern.java.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Optional;

@Service
public class FileDataService {

    private final FileDataRepository fileDataRepository;

    //@Value("${file.upload.directory}")
   // private String FOLDER_PATH;

    private final String FOLDER_PATH="C:\\Users\\kutlu\\OneDrive\\Masaüstü" +
            "\\companymanagmentSystem\\ProductManagmentSystem\\src\\main\\resources\\static\\product_images\\";;

    private final Logger logger = LoggerFactory.getLogger(FileDataService.class);

    public FileDataService(FileDataRepository fileDataRepository) throws IOException {
        this.fileDataRepository = fileDataRepository;
    }
    public FileData uploadImageToFileSystem(MultipartFile file) throws IOException {
        logger.info("FolderPath:"+FOLDER_PATH);
        logger.info("File name:"+file.getOriginalFilename());
        logger.info("File Type:"+file.getContentType());

        String filePath = FOLDER_PATH + file.getOriginalFilename();

      //  String fileName = file.getOriginalFilename();
       // String filePath = Paths.get(FOLDER_PATH, fileName).toString();

        try {
            FileData fileData = fileDataRepository.save(FileData.builder()
                    .name(file.getOriginalFilename())
                    .type(file.getContentType())
                    .filePath(filePath).build());

            file.transferTo(new File(filePath));

            return fileData;
        } catch (IOException e) {
            throw new FileUploadException("Error uploading file: " + file.getOriginalFilename(),e);
        }
    }

    public void deleteImage(Long fileId) throws IOException {

        Optional<FileData> optionalFileData = fileDataRepository.findById(fileId);

        if (optionalFileData.isPresent()) {
            FileData fileData = optionalFileData.get();
            String filePath = fileData.getFilePath();

            File file = new File(filePath);

            if (file.exists() && file.isFile()) {
                if (file.delete()) {
                    fileDataRepository.delete(fileData);
                } else {
                    throw new FileNotFoundException("An error occurred while deleting the file.");
                }
            } else {
                throw new FileNotFoundException("File not found or is not a regular file.");
            }
        } else {
            throw new FileNotFoundException("File not found in the database.");
        }
    }
}
