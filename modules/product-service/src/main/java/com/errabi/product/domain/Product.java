package com.errabi.product.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "products", schema = "schema1")
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

    private BigDecimal price;
    private String category;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
