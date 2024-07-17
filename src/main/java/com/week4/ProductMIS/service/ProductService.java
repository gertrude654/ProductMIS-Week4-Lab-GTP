package com.week4.ProductMIS.service;

import com.week4.ProductMIS.dto.ProductDTO;
import com.week4.ProductMIS.models.Product;
import com.week4.ProductMIS.mongoModels.ProductMongo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {

    Product getProduct(int ProductId);
    List<Product> getAllProducts();
    ProductDTO createProduct(ProductDTO productDTO);
    void updateProduct(Product product);
    boolean deleteProduct(int ProductId);
    Page<Product> findAllProductsSortedByPriceAsc(Pageable pageable) ;
    Page<Product> findProductsByCategory(int category, Pageable pageable);
    Page<Product> findAllProductsSortedByPriceDesc(Pageable pageable);
     void printTree() ;


    }
