package com.db.auction.service;

import com.db.auction.model.Bid;
import com.db.auction.repository.AuctionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.Instant;

@Component
public class AuctionService {
    @Autowired
    AuctionRepository auctionRepository;

    /**
     * save bidding info.
     *
     * @param bid Bid.
     * @return saved bid.
     */
    public Bid submitBid(Bid bid) {
        bid.setTimestamp(Timestamp.from(Instant.now()));
        return auctionRepository.save(bid);
    }

    /**
     * get the bid with highest bidding price corresponding to a product.
     *
     * @param productId product id.
     * @return highest bid.
     */
    public Bid getHighestBid(@Param("productId") Long productId) {

        return auctionRepository.findHighestBiddingPriceByProductId(productId);

    }

}
