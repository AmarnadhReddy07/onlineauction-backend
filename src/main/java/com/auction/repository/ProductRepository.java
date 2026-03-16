package com.auction.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.auction.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}