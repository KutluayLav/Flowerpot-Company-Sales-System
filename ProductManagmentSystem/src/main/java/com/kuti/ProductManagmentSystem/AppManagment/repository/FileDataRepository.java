package com.kuti.ProductManagmentSystem.AppManagment.repository;

import com.kuti.ProductManagmentSystem.AppManagment.model.FileData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FileDataRepository extends JpaRepository<FileData,Long> {
    Optional<FileData> findByName(String fileName);
}
