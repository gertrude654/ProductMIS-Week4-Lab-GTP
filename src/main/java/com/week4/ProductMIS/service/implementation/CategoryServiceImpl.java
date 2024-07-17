package com.week4.ProductMIS.service.implementation;

import com.week4.ProductMIS.models.Category;
import com.week4.ProductMIS.repository.CategoryRepo;
import com.week4.ProductMIS.service.CategoryService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepo repo;

    @Autowired
    public CategoryServiceImpl(CategoryRepo repo) {
        this.repo = repo;
    }

    @Override
    public Category getCategory(int categoryId) {
        Optional<Category> result = repo.findById(categoryId);

        Category theCategory = null;
        if (result.isPresent()) {
            theCategory = result.get();
        }else {
            throw new RuntimeException("Category id not found");
        }
        return theCategory;
    }

    @Override
    public List<Category> getAllCategories() {
        return repo.findAll();
    }

    @Override
    public Category createCategory(Category category) {
        return repo.save(category);
    }

    @Override
    @Transactional
    public void updateCategory(Category category) {
        repo.save(category);

    }

    @Override
    public boolean deleteCategory(int categoryId) {
        Optional<Category> deletedCategory = repo.findById(categoryId);
        if (deletedCategory.isPresent()) {
            repo.delete(deletedCategory.get());
            return true;
        }
        return false;
    }
}
