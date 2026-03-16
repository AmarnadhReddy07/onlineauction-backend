package com.auction.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.auction.model.Bid;
import com.auction.service.BidService;

@RestController
@RequestMapping("/api/bids")
@CrossOrigin(origins = "https://onlineauction-frontend.onrender.com")
public class BidController {

    @Autowired
    private BidService bidService;

    @PostMapping
    public Bid placeBid(@RequestBody Bid bid) {
        return bidService.placeBid(bid);
    }

    @GetMapping
    public List<Bid> getBids() {
        return bidService.getAllBids();
    }
}