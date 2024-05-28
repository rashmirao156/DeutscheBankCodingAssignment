package com.db.auction.repository;

import com.db.auction.model.Bid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AuctionRepository extends JpaRepository<Bid, Long> {

    @Query(value = "SELECT * FROM bid b WHERE b.product_Id = :productId AND b.bidding_Price = " +
            "(SELECT MAX(b1.bidding_Price) FROM bid b1 WHERE b1.product_Id = :productId)", nativeQuery = true)
    public Bid findHighestBiddingPriceByProductId(@Param("productId") Long productId);
}