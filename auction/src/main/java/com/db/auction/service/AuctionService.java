package com.db.auction.service;

import com.db.auction.exception.AuctionServiceException;
import com.db.auction.model.Bid;
import com.db.auction.repository.AuctionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

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

        try {
            return auctionRepository.save(bid);
        } catch (Exception e) {
            throw new AuctionServiceException("Exception while submitting the bid :" + e.getMessage(), e);
        }
    }

    /**
     * get the bid with highest bidding price corresponding to a product.
     *
     * @param productId product id.
     * @return highest bid.
     */
    public Bid getHighestBid(@Param("productId") Long productId) {
        try {
            return auctionRepository.findHighestBiddingPriceByProductId(productId);
        } catch (Exception e) {
            throw new AuctionServiceException("Exception while getting highest bid :" + e.getMessage(), e);
        }
    }

}
