package com.week4.ProductMIS.mongoModels;

import jakarta.persistence.Id;
import lombok.*;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;


@NoArgsConstructor
@AllArgsConstructor
@Data

@Profile("dev")
@Document(collection = "products")
public class ProductMongo {

    @Id
    private String id;
    private String productCode;
    private String description;
    private int quantity;
    @DBRef
    private CategoryMongo category;
}
