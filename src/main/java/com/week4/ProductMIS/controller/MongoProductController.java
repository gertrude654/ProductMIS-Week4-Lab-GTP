package com.week4.ProductMIS.controller;

import com.week4.ProductMIS.mongoModels.ProductMongo;
import com.week4.ProductMIS.servicemongo.ProductServiceMongo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Profile("dev")
@RestController
@RequestMapping("/api/mongo/products")
public class MongoProductController {

    @Autowired
    private ProductServiceMongo productService;

    // Get all products
    @GetMapping
    public ResponseEntity<List<ProductMongo>> getAllProducts() {
        List<ProductMongo> products = productService.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    // Get a specific product by ID
    @GetMapping("/{id}")
    public ResponseEntity<ProductMongo> getProduct(@PathVariable("id") String productId) {
        ProductMongo product = productService.getProduct(productId);
        return product != null
                ? new ResponseEntity<>(product, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Create a new product
    @PostMapping
    public ResponseEntity<ProductMongo> createProduct(@RequestBody ProductMongo product) {
        ProductMongo createdProduct = productService.createProduct(product);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    // Update an existing product
    @PutMapping("/{id}")
    public ResponseEntity<ProductMongo> updateProduct(
            @PathVariable("id") String productId,
            @RequestBody ProductMongo product) {
        product.setId(productId); // Ensure the ID is set for update
        productService.updateProduct(product);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    // Delete a product by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("id") String productId) {
        boolean isDeleted = productService.deleteProduct(productId);
        return isDeleted
                ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
