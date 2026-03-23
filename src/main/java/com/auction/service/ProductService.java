package com.auction.service;

import com.auction.model.Product;
import com.auction.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final UserService userService;

    public ProductService(ProductRepository productRepository, UserService userService) {
        this.productRepository = productRepository;
        this.userService       = userService;
    }

    // Add product — sets currentPrice = startingPrice on creation
    public Product addProduct(Product product) {
        if (product.getCurrentPrice() == null) {
            product.setCurrentPrice(product.getStartingPrice());
        }
        return productRepository.save(product);
    }

    // Add product with seller info from JWT email
    public Product addProductForSeller(Product product, String sellerEmail) {
        var seller = userService.getByEmail(sellerEmail);
        product.setSellerEmail(sellerEmail);
        product.setSellerName(seller.getName() != null ? seller.getName() : sellerEmail);
        if (product.getCurrentPrice() == null) {
            product.setCurrentPrice(product.getStartingPrice());
        }
        return productRepository.save(product);
    }

    // Update product
    public Product updateProduct(Long id, Product product) {
        Product existing = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        existing.setProductName(product.getProductName());
        existing.setDescription(product.getDescription());
        existing.setStartingPrice(product.getStartingPrice());
        existing.setCurrentPrice(product.getCurrentPrice());
        existing.setCategory(product.getCategory());
        existing.setImageUrl(product.getImageUrl());
        existing.setAuctionEndTime(product.getAuctionEndTime());
        return productRepository.save(existing);
    }

    // Get all products
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // Get one product
    public Product getProduct(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    // Delete product
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    // Update current bid price
    public void updateCurrentPrice(Long productId, Double newPrice) {
        Product product = getProduct(productId);
        product.setCurrentPrice(newPrice);
        productRepository.save(product);
    }
}