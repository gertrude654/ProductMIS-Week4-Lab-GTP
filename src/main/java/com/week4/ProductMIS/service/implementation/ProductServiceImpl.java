//package com.week4.ProductMIS.service.implementation;
//
//import com.week4.ProductMIS.models.Product;
//import com.week4.ProductMIS.repository.ProductRepo;
//import com.week4.ProductMIS.service.ProductService;
//import com.week4.ProductMIS.tree.BinaryTree;
//import jakarta.transaction.Transactional;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class ProductServiceImpl implements ProductService {
//
//    private ProductRepo repo;
//
//    private BinaryTree binaryTree = new BinaryTree();
//
//
//    @Autowired
//    public ProductServiceImpl(@Qualifier("productRepo") ProductRepo repo) {
//        this.repo = repo;
//    }
//
//    @Override
//    public Product getProduct(int ProductId) {
//        Optional<Product> result = repo.findById(ProductId);
//
//        Product theProduct = null;
//        if (result.isPresent()) {
//            theProduct = result.get();
//        }else {
//            throw new RuntimeException("Product id not found");
//        }
//        binaryTree.search(ProductId);
//        return theProduct;
//    }
//
//    @Override
//    public List<Product> getAllProducts() {
//        return repo.findAll();
//    }
//
//    @Override
//    public Product createProduct(Product product) {
//        binaryTree.addProduct(product);
//        return repo.save(product);
//
//    }
//
//    @Override
//    @Transactional
//    public void updateProduct(Product product) {
//        repo.save(product);
//    }
//
//    @Override
//    @Transactional
//    public boolean deleteProduct(int productId) {
//        Optional<Product> deletedProduct = repo.findById(productId);
//        if (deletedProduct.isPresent()) {
//            binaryTree.deleteProduct(productId);
//            repo.delete(deletedProduct.get());
//            return true;
//        }
//        return false;
//    }
//}

package com.week4.ProductMIS.service.implementation;

import com.week4.ProductMIS.dto.ProductDTO;
import com.week4.ProductMIS.models.Category;
import com.week4.ProductMIS.models.Product;
import com.week4.ProductMIS.repository.CategoryRepo;
import com.week4.ProductMIS.repository.ProductRepo;
import com.week4.ProductMIS.service.ProductService;
import com.week4.ProductMIS.tree.BinaryTree;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepo repo;
    private final BinaryTree<Product> binaryTree = new BinaryTree<>();

    @Autowired
    public ProductServiceImpl(@Qualifier("productRepo") ProductRepo repo) {
        this.repo = repo;
    }

    @PostConstruct
    public void init() {
        List<Product> products = repo.findAll();
        for (Product product : products) {
            binaryTree.add(product);
        }
    }

    @Override
    public Product getProduct(int productId) {
        Product searchProduct = new Product();
        searchProduct.setId(productId);
        Product product = binaryTree.search(searchProduct);
        if (product == null) {
            throw new RuntimeException("Product id not found");
        }
        return product;
    }

    @Override
    public List<Product> getAllProducts() {
        return repo.findAll();
    }

//    @Override
//    public Product createProduct(Product product) {
//        Product savedProduct = repo.save(product);
//        binaryTree.add(savedProduct);
//        return savedProduct;
//    }

    @Autowired
    private CategoryRepo categoryRepo;

    @Override
    public ProductDTO createProduct(ProductDTO productDTO) {
        Category category = categoryRepo.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        Product product = new Product();
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setCategory(category);

        Product savedProduct = repo.save(product);
        return convertToDTO(savedProduct);
    }

    private ProductDTO convertToDTO(Product product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setPrice(product.getPrice());
        productDTO.setCategoryId(product.getCategory().getId());
        return productDTO;
    }

    @Override
    @Transactional
    public void updateProduct(Product product) {
        repo.save(product);
        binaryTree.delete(product); // Remove the old product
        binaryTree.add(product); // Add the updated product
    }

    @Override
    @Transactional
    public boolean deleteProduct(int productId) {
        Optional<Product> deletedProduct = repo.findById(productId);
        if (deletedProduct.isPresent()) {
            Product product = deletedProduct.get();
            binaryTree.delete(product);
            repo.delete(product);
            return true;
        }
        return false;
    }

    @Override
    public Page<Product> findProductsByCategory(int category, Pageable pageable) {
        return repo.findByCategory(category, pageable);
    }

    @Override
    public Page<Product> findAllProductsSortedByPriceAsc(Pageable pageable) {
        return repo.findAllOrderByPriceAsc(pageable);
    }

    @Override
    public Page<Product> findAllProductsSortedByPriceDesc(Pageable pageable) {
        return repo.findAllOrderByPriceDesc(pageable);
    }

   // @Override
//    public List<Product> getProductsByCategory(int categoryId) {
//        return binaryTree.getItemsByCategory(categoryId);
//    }
    @Override
    public void printTree() {
        binaryTree.inOrder();
    }
}

