package com.kuti.ProductManagmentSystem.AppManagment.repository;

import com.kuti.ProductManagmentSystem.AppManagment.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {



}
