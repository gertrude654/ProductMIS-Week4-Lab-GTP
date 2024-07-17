package com.week4.ProductMIS.controller;

import com.week4.ProductMIS.dto.ProductDTO;
import com.week4.ProductMIS.models.Product;
import com.week4.ProductMIS.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private  ProductService productService;
    private  PagedResourcesAssembler<Product> pagedResourcesAssembler;

    @Autowired
    public ProductController(ProductService productService, PagedResourcesAssembler<Product> pagedResourcesAssembler) {
        this.productService = productService;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }
    @PostMapping("/add")
    public ResponseEntity<ProductDTO> addProduct(@RequestBody ProductDTO product) {
        ProductDTO createdProduct = productService.createProduct(product);

        EntityModel<ProductDTO> entityModel = EntityModel.of(createdProduct,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ProductController.class).getProductById(createdProduct.getId())).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ProductController.class).getAllProducts()).withRel("products"));

        //return ResponseEntity.ok(entityModel);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);

    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Product>> getProductById(@PathVariable("id") int id) {
        Product product = productService.getProduct(id);

        if (product == null) {
            return ResponseEntity.notFound().build();
        }

        EntityModel<Product> entityModel = EntityModel.of(product,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ProductController.class).getProductById(id)).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ProductController.class).getAllProducts()).withRel("products"));

        return ResponseEntity.ok(entityModel);
    }

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Product>>> getAllProducts() {
        List<Product> products = productService.getAllProducts();

        List<EntityModel<Product>> entityModels = products.stream()
                .map(product -> EntityModel.of(product,
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ProductController.class).getProductById(product.getId())).withSelfRel(),
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ProductController.class).getAllProducts()).withRel("products")))
                .collect(Collectors.toList());

        CollectionModel<EntityModel<Product>> collectionModel = CollectionModel.of(entityModels,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ProductController.class).getAllProducts()).withSelfRel());

        return ResponseEntity.ok(collectionModel);
    }
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Product>> updateProduct(@PathVariable("id") int id, @RequestBody Product updatedProduct) {
        Product product = productService.getProduct(id);

        if (product == null) {
            return ResponseEntity.notFound().build();
        }

        product.setName(updatedProduct.getName());
        product.setPrice(updatedProduct.getPrice());

        productService.updateProduct(product);

        EntityModel<Product> entityModel = EntityModel.of(product,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ProductController.class).getProductById(id)).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ProductController.class).getAllProducts()).withRel("products"));

        return ResponseEntity.ok(entityModel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("id") int id) {
        boolean deleted = productService.deleteProduct(id);

        if (!deleted) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
    @GetMapping("/category")
    public PagedModel<EntityModel<Product>> getProductsByCategory(
            @RequestParam(required = false, defaultValue = "all") int category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> productsPage = productService.findProductsByCategory(category, pageable);
        return pagedResourcesAssembler.toModel(productsPage);
    }
//    @GetMapping("/page")
//    public Page<Product> getProductsByCategory(@RequestParam int category,
//                                               @RequestParam(defaultValue = "0") int page,
//                                               @RequestParam(defaultValue = "10") int size) {
//        Pageable pageable = PageRequest.of(page, size);
//        return productService.findProductsByCategory(category, pageable);
//    }

    @GetMapping("/sorted/asc")
    public Page<Product> getAllProductsSortedByPriceAsc(@RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productService.findAllProductsSortedByPriceAsc(pageable);
    }

    @GetMapping("/sorted/desc")
    public Page<Product> getAllProductsSortedByPriceDesc(@RequestParam(defaultValue = "0") int page,
                                                         @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productService.findAllProductsSortedByPriceDesc(pageable);
    }
//    @GetMapping("/{categoryId}/products")
//    public List<Product> getProductsByCategory(@PathVariable int categoryId) {
//        return productService.getProductsByCategory(categoryId);
//    }
//    @GetMapping("/page")
//    public Page<Product> getProductsByCategory(@RequestParam int category,
//                                               @RequestParam int page,
//                                               @RequestParam int size) {
//        Pageable pageable = PageRequest.of(page, size);
//        return productService.findProductsByCategory(category, pageable);
//    }
//
//    @GetMapping("/sorted/asc")
//    public Page<Product> getAllProductsSortedByPriceAsc(@RequestParam int page, @RequestParam int size) {
//        Pageable pageable = PageRequest.of(page, size);
//        return productService.findAllProductsSortedByPriceAsc(pageable);
//    }
//
//    @GetMapping("/sorted/desc")
//    public Page<Product> getAllProductsSortedByPriceDesc(@RequestParam int page, @RequestParam int size) {
//        Pageable pageable = PageRequest.of(page, size);
//        return productService.findAllProductsSortedByPriceDesc(pageable);
//    }
}