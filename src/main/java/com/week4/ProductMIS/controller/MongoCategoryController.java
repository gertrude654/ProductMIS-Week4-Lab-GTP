package com.week4.ProductMIS.controller;

import com.week4.ProductMIS.mongoModels.CategoryMongo;
import com.week4.ProductMIS.servicemongo.CategoryServiceMongo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Profile("dev")
@RestController
@RequestMapping("/api/mongo/categories")
public class MongoCategoryController {

    @Autowired
    private CategoryServiceMongo categoryService;

    // Get all categories
    @GetMapping
    public ResponseEntity<List<CategoryMongo>> getAllCategories() {
        List<CategoryMongo> categories = categoryService.getAllCategories();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    // Get a specific category by ID
    @GetMapping("/{id}")
    public ResponseEntity<CategoryMongo> getCategory(@PathVariable("id") String categoryId) {
        CategoryMongo category = categoryService.getCategory(categoryId);
        return category != null
                ? new ResponseEntity<>(category, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Create a new category
    @PostMapping
    public ResponseEntity<CategoryMongo> createCategory(@RequestBody CategoryMongo category) {
        CategoryMongo createdCategory = categoryService.createCategory(category);
        return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
    }

    // Update an existing category
    @PutMapping("/{id}")
    public ResponseEntity<CategoryMongo> updateCategory(
            @PathVariable("id") String categoryId,
            @RequestBody CategoryMongo category) {
        category.setId(categoryId); // Ensure the ID is set for update
        categoryService.updateCategory(category);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    // Delete a category by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable("id") String categoryId) {
        boolean isDeleted = categoryService.deleteCategory(categoryId);
        return isDeleted
                ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
