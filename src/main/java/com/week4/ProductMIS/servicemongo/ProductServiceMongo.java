package com.week4.ProductMIS.servicemongo;

import com.week4.ProductMIS.mongoModels.ProductMongo;
import org.springframework.context.annotation.Profile;

import java.util.List;

@Profile("dev")
public interface ProductServiceMongo {

    ProductMongo getProduct(String ProductId);
    List<ProductMongo> getAllProducts();
    ProductMongo createProduct(ProductMongo product);
    void updateProduct(ProductMongo product);
    boolean deleteProduct(String ProductId);

}
