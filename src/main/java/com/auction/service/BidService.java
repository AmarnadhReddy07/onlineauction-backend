package com.auction.service;

import com.auction.model.Bid;
import com.auction.model.Product;
import com.auction.model.User;
import com.auction.repository.BidRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BidService {

    private final BidRepository bidRepository;
    private final ProductService productService;
    private final UserService userService;

    public BidService(BidRepository bidRepository,
                      ProductService productService,
                      UserService userService) {
        this.bidRepository  = bidRepository;
        this.productService = productService;
        this.userService    = userService;
    }

    // Place a bid — validates amount and updates product price
    @Transactional
    public Bid placeBid(Bid bid) {
        Product product = productService.getProduct(bid.getProductId());

        if (!"ACTIVE".equals(product.getStatus()))
            throw new RuntimeException("This auction has ended.");

        double minBid = product.getCurrentPrice() != null
                ? product.getCurrentPrice() + 1
                : product.getStartingPrice();

        if (bid.getAmount() < minBid)
            throw new RuntimeException("Bid must be at least " + minBid);

        // Set bidder name if email provided
        if (bid.getBidderEmail() != null) {
            try {
                User bidder = userService.getByEmail(bid.getBidderEmail());
                bid.setBidderName(bidder.getName() != null ? bidder.getName() : bid.getBidderEmail());
            } catch (Exception ignored) {}
        }

        Bid saved = bidRepository.save(bid);
        productService.updateCurrentPrice(bid.getProductId(), bid.getAmount());
        return saved;
    }

    // Place bid from authenticated user
    @Transactional
    public Bid placeBidForUser(Long productId, Double amount, String bidderEmail) {
        Bid bid = new Bid();
        bid.setProductId(productId);
        bid.setAmount(amount);
        bid.setBidderEmail(bidderEmail);
        return placeBid(bid);
    }

    // Get all bids
    public List<Bid> getAllBids() {
        return bidRepository.findAll();
    }

    // Get bids for a product
    public List<Bid> getBidsForProduct(Long productId) {
        return bidRepository.findByProductIdOrderByAmountDesc(productId);
    }

    // Get bids by user email
    public List<Bid> getMyBids(String bidderEmail) {
        return bidRepository.findByBidderEmailOrderByPlacedAtDesc(bidderEmail);
    }
}