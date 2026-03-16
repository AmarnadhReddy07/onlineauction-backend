package com.auction.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.auction.model.Bid;

public interface BidRepository extends JpaRepository<Bid, Long> {
}