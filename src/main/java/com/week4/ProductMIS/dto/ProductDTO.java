package com.week4.ProductMIS.dto;


import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductDTO {
    private int id;
    private String name;
    private String description;
    private BigDecimal price;
    private int categoryId;
}

