//package com.week4.ProductMIS.tree;
//
//import com.week4.ProductMIS.models.Product;
//import org.springframework.stereotype.Component;
//
//@Component
//public class BinaryTree {
//    private TreeNode root;
//
//    public BinaryTree() {
//        this.root = null;
//    }
//
//    // Method to add a product to the tree
//    public void addProduct(Product product) {
//        root = addRecursive(root, product);
//    }
//
//    private TreeNode addRecursive(TreeNode node, Product product) {
//        if (node == null) {
//            return new TreeNode(product);
//        }
//
//        if (product.getId() < node.getProduct().getId()) {
//            node.setLeft(addRecursive(node.getLeft(), product));
//        } else if (product.getId() > node.getProduct().getId()) {
//            node.setRight(addRecursive(node.getRight(), product));
//        } else {
//            // Handle duplicates if necessary
//        }
//
//        return node;
//    }
//
//    // Method to delete a product from the tree
//    public void deleteProduct(int id) {
//        root = deleteRecursive(root, id);
//    }
//
//    private TreeNode deleteRecursive(TreeNode node, int id) {
//        if (node == null) {
//            return null;
//        }
//
//        if (id == node.getProduct().getId()) {
//            // Node to delete found
//
//            // Case 1: no children
//            if (node.getLeft() == null && node.getRight() == null) {
//                return null;
//            }
//
//            // Case 2: one child
//            if (node.getRight() == null) {
//                return node.getLeft();
//            }
//
//            if (node.getLeft() == null) {
//                return node.getRight();
//            }
//
//            // Case 3: two children
//            Product smallestProduct = findSmallestProduct(node.getRight());
//            node.setProduct(smallestProduct);
//            node.setRight(deleteRecursive(node.getRight(), smallestProduct.getId()));
//            return node;
//        }
//
//        if (id < node.getProduct().getId()) {
//            node.setLeft(deleteRecursive(node.getLeft(), id));
//            return node;
//        }
//
//        node.setRight(deleteRecursive(node.getRight(), id));
//        return node;
//    }
//
//    private Product findSmallestProduct(TreeNode node) {
//        return node.getLeft() == null ? node.getProduct() : findSmallestProduct(node.getLeft());
//    }
//
//    // Method to search for a product by id
//    public void searchProduct(int id) {
//        searchRecursive(root, id);
//    }
//
//    private Product searchRecursive(TreeNode node, int id) {
//        if (node == null || node.getProduct().getId() == id) {
//            return node == null ? null : node.getProduct();
//        }
//
//        return id < node.getProduct().getId() ? searchRecursive(node.getLeft(), id) : searchRecursive(node.getRight(), id);
//    }
//}
//


package com.week4.ProductMIS.tree;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BinaryTree<T extends Comparable<T>> {
    private TreeNode<T> root;

    public BinaryTree() {
        this.root = null;
    }

    // Method to add an item to the tree
    public void add(T data) {
        root = addRecursive(root, data);
    }

    private TreeNode<T> addRecursive(TreeNode<T> node, T data) {
        if (node == null) {
            return new TreeNode<>(data);
        }

        if (data.compareTo(node.getData()) < 0) {
            node.setLeft(addRecursive(node.getLeft(), data));
        } else if (data.compareTo(node.getData()) > 0) {
            node.setRight(addRecursive(node.getRight(), data));
        } else {
            // Handle duplicates if necessary
        }

        return node;
    }

    // Method to delete an item from the tree
    public void delete(T data) {
        root = deleteRecursive(root, data);
    }

    private TreeNode<T> deleteRecursive(TreeNode<T> node, T data) {
        if (node == null) {
            return null;
        }

        if (data.equals(node.getData())) {
            // Node to delete found

            // Case 1: no children
            if (node.getLeft() == null && node.getRight() == null) {
                return null;
            }

            // Case 2: one child
            if (node.getRight() == null) {
                return node.getLeft();
            }

            if (node.getLeft() == null) {
                return node.getRight();
            }

            // Case 3: two children
            T smallestData = findSmallestData(node.getRight());
            node.setData(smallestData);
            node.setRight(deleteRecursive(node.getRight(), smallestData));
            return node;
        }

        if (data.compareTo(node.getData()) < 0) {
            node.setLeft(deleteRecursive(node.getLeft(), data));
            return node;
        }

        node.setRight(deleteRecursive(node.getRight(), data));
        return node;
    }

    private T findSmallestData(TreeNode<T> node) {
        return node.getLeft() == null ? node.getData() : findSmallestData(node.getLeft());
    }

    // Method to search for an item
    public T search(T data) {
        return searchRecursive(root, data);
    }

    private T searchRecursive(TreeNode<T> node, T data) {
        if (node == null || node.getData().equals(data)) {
            return node == null ? null : node.getData();
        }

        return data.compareTo(node.getData()) < 0 ? searchRecursive(node.getLeft(), data) : searchRecursive(node.getRight(), data);
    }


    // Method to perform in-order traversal and print categories and their items
    public void inOrder() {
        inOrderRec(root);
    }

    private void inOrderRec(TreeNode<T> node) {
        if (node != null) {
            inOrderRec(node.getLeft());
            System.out.println(" Category: " + node.getCategory() + ", Items: " + node.getData());
            inOrderRec(node.getRight());
        }
    }

}
