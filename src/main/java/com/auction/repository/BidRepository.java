package com.auction.repository;

import com.auction.model.Bid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BidRepository extends JpaRepository<Bid, Long> {
    List<Bid> findByProductIdOrderByAmountDesc(Long productId);
    List<Bid> findByBidderEmailOrderByPlacedAtDesc(String bidderEmail);
    Optional<Bid> findFirstByProductIdOrderByAmountDesc(Long productId);
}