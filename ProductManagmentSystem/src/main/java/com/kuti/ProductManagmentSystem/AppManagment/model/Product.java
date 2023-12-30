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
            ,fetch = FetchType.EAGER)
    @JoinColumn(name = "file_id")
    private FileData fileData;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "product_category",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Category category;

}


