package com.kuti.ProductManagmentSystem.AppManagment.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "Product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productName;

    private String description;

    private String features;

    private BigDecimal price;

    private long quantity;

    private boolean status;

    @OneToOne(cascade = CascadeType.ALL,
            orphanRemoval = true
            ,fetch = FetchType.LAZY)
    @JoinColumn(name = "file_id")
    private FileData fileData;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id", referencedColumnName = "category_id")
    private Category category;

}


