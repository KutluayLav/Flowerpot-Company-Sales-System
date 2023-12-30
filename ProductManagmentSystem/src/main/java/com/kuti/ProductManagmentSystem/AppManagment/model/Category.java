package com.kuti.ProductManagmentSystem.AppManagment.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "categories")
@Builder
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    @NotNull(message = "Name is Cannot be Empty!")
    private String name;

    @OneToMany(mappedBy = "category",orphanRemoval = true)
    private List<Product> product;


}
