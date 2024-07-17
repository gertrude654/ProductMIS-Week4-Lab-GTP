package com.week4.ProductMIS.servicemongo.implementation;

import com.week4.ProductMIS.mongoModels.CategoryMongo;
import com.week4.ProductMIS.repository.CategoryRepoMongo;
import com.week4.ProductMIS.servicemongo.CategoryServiceMongo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Profile("dev")
@Service
public class CategoryServiceImplMongo implements CategoryServiceMongo {

    private CategoryRepoMongo repo;

    @Autowired
    public CategoryServiceImplMongo(CategoryRepoMongo repo) {
        this.repo = repo;
    }

    @Override
    public CategoryMongo getCategory(String categoryId) {
        return null;
    }

    @Override
    public List<CategoryMongo> getAllCategories() {
        return repo.findAll();
    }

    @Override
    public CategoryMongo createCategory(CategoryMongo category) {
        return repo.save(category);
    }

    @Override
    public void updateCategory(CategoryMongo category) {
        repo.save(category);
    }

    @Override
    public boolean deleteCategory(String categoryId) {
        Optional<CategoryMongo> deletedCategory = repo.findById(categoryId);
        if (deletedCategory.isPresent()) {
            CategoryMongo category = deletedCategory.get();
            repo.delete(category);
            return true;
        }
        return false;
    }
}
