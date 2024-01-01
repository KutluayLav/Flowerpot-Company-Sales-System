package com.kuti.ProductManagmentSystem.AppManagment.repository;

import com.kuti.ProductManagmentSystem.AppManagment.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

    boolean existsByProductName(String name);

    List<Product> findAllById(long id);

}
