package com.week4.ProductMIS.repository;

import com.week4.ProductMIS.models.Product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepo extends JpaRepository<Product, Integer> {

    @Query("SELECT p FROM Product p WHERE p.category = :category")
    Page<Product> findByCategory(@Param("category") int category, Pageable pageable);

    @Query("SELECT p FROM Product p ORDER BY p.price ASC")
    Page<Product> findAllOrderByPriceAsc(Pageable pageable);

    @Query("SELECT p FROM Product p ORDER BY p.price DESC")
    Page<Product> findAllOrderByPriceDesc(Pageable pageable);
}
