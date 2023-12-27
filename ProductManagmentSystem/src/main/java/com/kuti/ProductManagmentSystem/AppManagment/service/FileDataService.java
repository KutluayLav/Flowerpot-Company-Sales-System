package com.kuti.ProductManagmentSystem.AppManagment.service;
import com.kuti.ProductManagmentSystem.AppManagment.exception.FileNotFoundException;
import com.kuti.ProductManagmentSystem.AppManagment.exception.FileUploadException;
import com.kuti.ProductManagmentSystem.AppManagment.model.FileData;
import com.kuti.ProductManagmentSystem.AppManagment.repository.FileDataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

@Service
public class FileDataService {

    private final FileDataRepository fileDataRepository;

    @Value("${file.directory}")
    private String FOLDER_NAME;
    private final String UPLOAD_DIR=new ClassPathResource("static/product_images/").getFile().getAbsolutePath();
    private final Logger logger = LoggerFactory.getLogger(FileDataService.class);

    public FileDataService(FileDataRepository fileDataRepository) throws IOException {
        this.fileDataRepository = fileDataRepository;
    }
    public FileData uploadImageToFileSystem(MultipartFile file) throws IOException {

        String filePath = UPLOAD_DIR +File.separator+file.getOriginalFilename();

        logger.info(FOLDER_NAME +"Folder name adres");

        try {
            FileData fileData = fileDataRepository.save(FileData.builder()
                    .name(file.getOriginalFilename())
                    .type(file.getContentType())
                    .filePath(file.getOriginalFilename()).build());

            file.transferTo(new File(filePath));
            logger.info("Resim Yuklendi:"+file.getOriginalFilename());
            return fileData;
        } catch (IOException e) {
            logger.error("Hata Resim Yuklenemedi !!! :"+file.getOriginalFilename());
            throw new FileUploadException("Error uploading file: " + file.getOriginalFilename(),e);
        }
    }

    public void deleteImage(Long fileId) throws IOException {

        Optional<FileData> optionalFileData = fileDataRepository.findById(fileId);

        if (optionalFileData.isPresent()) {
            FileData fileData = optionalFileData.get();

            String filePath =UPLOAD_DIR + File.separator +fileData.getFilePath();

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
