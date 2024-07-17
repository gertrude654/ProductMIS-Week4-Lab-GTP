package com.week4.ProductMIS.service;

import com.week4.ProductMIS.models.Category;

import java.util.List;

public interface CategoryService{

    Category getCategory(int categoryId);
    List<Category> getAllCategories();
    Category createCategory(Category category);
    void updateCategory(Category category);
    boolean deleteCategory(int categoryId);
}
