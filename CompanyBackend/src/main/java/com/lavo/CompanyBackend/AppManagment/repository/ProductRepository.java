package com.lavo.CompanyBackend.AppManagment.repository;

import com.lavo.CompanyBackend.AppManagment.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {



}
