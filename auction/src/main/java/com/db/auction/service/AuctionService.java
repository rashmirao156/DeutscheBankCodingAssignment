package com.db.auction.service;

import com.db.auction.model.Bid;
import com.db.auction.repository.AuctionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

@Component
public class AuctionService {
    @Autowired
    AuctionRepository auctionRepository;


    public Bid submitBid(Bid bid) {
        return auctionRepository.save(bid);
    }

    public Bid getHighestBid(@Param("productId") Long productId) {
    return auctionRepository.findHighestBiddingPriceByProductId(productId);
    }

}
