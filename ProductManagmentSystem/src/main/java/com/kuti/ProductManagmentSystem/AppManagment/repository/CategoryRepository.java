package com.kuti.ProductManagmentSystem.AppManagment.repository;

import com.kuti.ProductManagmentSystem.AppManagment.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {

    boolean existsCategoryByName(String name);

    Optional<Category> findByName(String name);

}
