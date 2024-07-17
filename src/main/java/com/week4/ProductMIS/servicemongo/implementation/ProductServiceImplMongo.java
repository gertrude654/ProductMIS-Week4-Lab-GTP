package com.week4.ProductMIS.servicemongo.implementation;

import com.week4.ProductMIS.mongoModels.ProductMongo;
import com.week4.ProductMIS.repository.ProductRepoMongo;
import com.week4.ProductMIS.servicemongo.ProductServiceMongo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Profile("dev")
@Service
public class ProductServiceImplMongo implements ProductServiceMongo {

    private ProductRepoMongo repo;

    @Autowired
    public ProductServiceImplMongo(ProductRepoMongo repo) {
        this.repo = repo;
    }

    @Override
    public ProductMongo createProduct(ProductMongo product) {
        return repo.save(product);
    }

    @Override
    public ProductMongo getProduct(String productId) {
        return repo.findById(productId).orElse(null);
    }

    @Override
    public List<ProductMongo> getAllProducts() {
        return repo.findAll();
    }

    @Override
    public void updateProduct(ProductMongo product) {
         repo.save(product);
    }

    @Override
    public boolean deleteProduct(String ProductId) {
        Optional<ProductMongo> deletedProduct = repo.findById(ProductId);
        if (deletedProduct.isPresent()) {
            ProductMongo product = deletedProduct.get();
            repo.delete(product);
            return true;
        }
        return false;
    }
}
