package com.week4.ProductMIS.servicemongo;

import com.week4.ProductMIS.mongoModels.CategoryMongo;
import org.springframework.context.annotation.Profile;

import java.util.List;

@Profile("dev")
public interface CategoryServiceMongo {

    CategoryMongo getCategory(String categoryId);
    List<CategoryMongo> getAllCategories();
    CategoryMongo createCategory(CategoryMongo category);
    void updateCategory(CategoryMongo category);
    boolean deleteCategory(String categoryId);
}
