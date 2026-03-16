package com.auction.service;

import java.util.List;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import com.auction.model.Product;
import com.auction.repository.ProductRepository;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository = null;

    // ───── Add Product ─────
    public Product addProduct(Product product) {
        product.setCurrentPrice(product.getStartingPrice()); // important for auction
        return productRepository.save(product);
    }

    // ───── Update Product ─────
    public Product updateProduct(Long id, Product product) {

        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        existingProduct.setProductName(product.getProductName());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setStartingPrice(product.getStartingPrice());
        existingProduct.setCurrentPrice(product.getCurrentPrice()); // FIXED
        existingProduct.setAuctionEndTime(product.getAuctionEndTime()); // IMPORTANT

        return productRepository.save(existingProduct);
    }

    // ───── Get All Products ─────
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // ───── Get One Product ─────
    public Product getProduct(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    // ───── Delete Product ─────
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}