package com.lavo.CompanyBackend.AppManagment.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String productName;

    private String description;

    private String features;

    private BigDecimal price;

    private long quantity;

    private String status;

    @OneToOne(cascade = CascadeType.ALL,
            orphanRemoval = true
            ,fetch = FetchType.LAZY)
    @JoinColumn(name = "file_id")
    private FileData fileData;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id", referencedColumnName = "category_id")
    private Category category;

}


