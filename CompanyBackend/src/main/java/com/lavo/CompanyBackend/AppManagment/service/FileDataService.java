package com.lavo.CompanyBackend.AppManagment.service;

import com.lavo.CompanyBackend.AppManagment.exception.FileNotFoundException;
import com.lavo.CompanyBackend.AppManagment.exception.FileUploadException;
import com.lavo.CompanyBackend.AppManagment.model.FileData;
import com.lavo.CompanyBackend.AppManagment.repository.FileDataRepository;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

@Service
public class FileDataService {

    private final FileDataRepository fileDataRepository;
    private final ResourceLoader resourceLoader;
    private String FOLDER_PATH;

    public FileDataService(FileDataRepository fileDataRepository, ResourceLoader resourceLoader) throws IOException {
        this.fileDataRepository = fileDataRepository;
        this.resourceLoader = resourceLoader;

        Resource resource = resourceLoader.getResource("/static/product_images/");

        this.FOLDER_PATH=resource.getFile().getAbsolutePath();
    }
    public FileData uploadImageToFileSystem(MultipartFile file) {

        String filePath = FOLDER_PATH + file.getOriginalFilename();

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
