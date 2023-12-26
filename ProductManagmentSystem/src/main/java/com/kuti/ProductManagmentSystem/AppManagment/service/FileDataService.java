package com.kuti.ProductManagmentSystem.AppManagment.service;

import com.kuti.ProductManagmentSystem.AppManagment.exception.FileNotFoundException;
import com.kuti.ProductManagmentSystem.AppManagment.exception.FileUploadException;
import com.kuti.ProductManagmentSystem.AppManagment.model.FileData;
import com.kuti.ProductManagmentSystem.AppManagment.repository.FileDataRepository;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${file.upload.directory}")
    private String FOLDER_PATH;


    public FileDataService(FileDataRepository fileDataRepository, ResourceLoader resourceLoader) throws IOException {
        this.fileDataRepository = fileDataRepository;
        this.resourceLoader = resourceLoader;
    }
    public FileData uploadImageToFileSystem(MultipartFile file) throws IOException {

        Resource resource = resourceLoader.getResource(FOLDER_PATH);

        String filePath = resource.getFile().getAbsolutePath() + file.getOriginalFilename();

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
