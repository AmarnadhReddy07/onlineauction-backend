package com.auction.controller;

import com.auction.model.Bid;
import com.auction.service.BidService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/bids")
@CrossOrigin(origins = "http://localhost:5173")
public class BidController {

    private final BidService bidService;

    public BidController(BidService bidService) {
        this.bidService = bidService;
    }

    // POST /api/bids — protected
    // Body: { "productId": 1, "amount": 5000.0 }
    @PostMapping
    public ResponseEntity<?> placeBid(
            @RequestBody Map<String, Object> payload,
            @AuthenticationPrincipal UserDetails userDetails) {
        try {
            Long productId   = Long.parseLong(payload.get("productId").toString());
            Double amount    = Double.parseDouble(payload.get("amount").toString());
            String email     = userDetails.getUsername(); // email from JWT
            Bid bid = bidService.placeBidForUser(productId, amount, email);
            return ResponseEntity.ok(bid);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", e.getMessage()));
        }
    }

    // GET /api/bids — protected (all bids, admin use)
    @GetMapping
    public List<Bid> getBids() {
        return bidService.getAllBids();
    }

    // GET /api/bids/product/{productId} — public
    @GetMapping("/product/{productId}")
    public List<Bid> getBidsForProduct(@PathVariable Long productId) {
        return bidService.getBidsForProduct(productId);
    }

    // GET /api/bids/my — protected (current user's bids)
    @GetMapping("/my")
    public ResponseEntity<?> getMyBids(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(bidService.getMyBids(userDetails.getUsername()));
    }
}