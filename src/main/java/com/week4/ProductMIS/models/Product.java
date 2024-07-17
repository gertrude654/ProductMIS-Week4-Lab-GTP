package com.week4.ProductMIS.models;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data

@Entity
public class Product implements Comparable<Product>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String productCode;
    private String name;
    private BigDecimal price;
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    //@JsonBackReference // Use this to break circular reference
    private Category category;

    @Override
    public int compareTo(Product other) {
        return Integer.compare(this.id, other.id);
    }

}
