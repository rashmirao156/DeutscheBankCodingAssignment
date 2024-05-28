package com.db.auction.repository;

import com.db.auction.model.Bid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AuctionRepository extends JpaRepository<Bid, Long> {

    @Query(value = "SELECT * FROM bid b WHERE b.product_Id = :productId AND b.bidding_Price = " +
            "(SELECT MAX(b1.bidding_Price) FROM bid b1 WHERE b1.product_Id = :productId) " +
            "AND b.updated_At = (SELECT MIN(b2.updated_At) FROM bid b2 WHERE b2.product_Id = :productId AND b2.bidding_Price = (SELECT MAX(b3.bidding_Price) FROM bid b3 WHERE b3.product_Id = :productId)) " +
            "LIMIT 1", nativeQuery = true)
    public Bid findHighestBiddingPriceByProductId(@Param("productId") Long productId);
}