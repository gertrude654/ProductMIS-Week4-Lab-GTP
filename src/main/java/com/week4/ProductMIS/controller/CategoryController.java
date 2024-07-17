package com.week4.ProductMIS.controller;

//package com.week4.CategoryMIS.controller;
//
//import com.week4.CategoryMIS.models.Category;
//import com.week4.CategoryMIS.service.CategoryService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/Categorys")
//public class CategoryController {
//
//    private CategoryService CategoryService;
//
//    @Autowired
//    public CategoryController(CategoryService CategoryService) {
//        this.CategoryService = CategoryService;
//    }
//
////    @PostMapping
////    public Category createCategory(@RequestBody Category Category) {
////        return CategoryService.createCategory(Category);
////    }
//@PostMapping("/add")
//public ResponseEntity<Category> addCategory(@RequestBody Category Category) {
//    CategoryService.createCategory(Category);
//    return ResponseEntity.ok(Category);
//}
//
//    @GetMapping
//    public List<Category> getAllCategory() {
//        return CategoryService.getAllCategory();
//    }
//}


import com.week4.ProductMIS.models.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.week4.ProductMIS.service.CategoryService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    private final CategoryService CategoryService;

    @Autowired
    public CategoryController(CategoryService CategoryService) {
        this.CategoryService = CategoryService;
    }

    @PostMapping("/add")
    public ResponseEntity<EntityModel<Category>> addCategory(@RequestBody Category Category) {
        Category createdCategory = CategoryService.createCategory(Category);

        EntityModel<Category> entityModel = EntityModel.of(createdCategory,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CategoryController.class).getCategoryById(createdCategory.getId())).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CategoryController.class).getAllCategory()).withRel("Category"));

        return ResponseEntity.ok(entityModel);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Category>> getCategoryById(@PathVariable("id") int id) {
        Category Category = CategoryService.getCategory(id);

        if (Category == null) {
            return ResponseEntity.notFound().build();
        }

        EntityModel<Category> entityModel = EntityModel.of(Category,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CategoryController.class).getCategoryById(id)).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CategoryController.class).getAllCategory()).withRel("Category"));

        return ResponseEntity.ok(entityModel);
    }

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Category>>> getAllCategory() {
        List<Category> Categories = CategoryService.getAllCategories();

        List<EntityModel<Category>> entityModels = Categories.stream()
                .map(Category -> EntityModel.of(Category,
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CategoryController.class).getCategoryById(Category.getId())).withSelfRel(),
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CategoryController.class).getAllCategory()).withRel("Category")))
                .collect(Collectors.toList());

        CollectionModel<EntityModel<Category>> collectionModel = CollectionModel.of(entityModels,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CategoryController.class).getAllCategory()).withSelfRel());

        return ResponseEntity.ok(collectionModel);
    }
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Category>> updateCategory(@PathVariable("id") int id, @RequestBody Category updatedCategory) {
        Category Category = CategoryService.getCategory(id);

        if (Category == null) {
            return ResponseEntity.notFound().build();
        }

        Category.setCategoryCode(updatedCategory.getCategoryCode());
      //  Category.(updatedCategory.getDescription());

        CategoryService.updateCategory(Category);

        EntityModel<Category> entityModel = EntityModel.of(Category,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CategoryController.class).getCategoryById(id)).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CategoryController.class).getAllCategory()).withRel("Category"));

        return ResponseEntity.ok(entityModel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable("id") int id) {
        boolean deleted = CategoryService.deleteCategory(id);

        if (!deleted) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}
