package com.db.auction.repository;

import com.db.auction.model.Bid;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.sql.Timestamp;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class AuctionRepositoryTest {

    @Autowired
    AuctionRepository auctionRepository;

    @BeforeEach
    public void setUp(){
        auctionRepository.deleteAll();
        auctionRepository.save(getBid1());
        auctionRepository.save(getBid2());
        auctionRepository.save(getBid3());
        auctionRepository.save(getBid4());

    }

    private static Bid getBid1() {
        Bid bid1 = new Bid();
        bid1.setId(1);
        bid1.setProductId(101);
        bid1.setBiddingPrice(100);
        bid1.setTimestamp(Timestamp.from(Instant.now()));
        return bid1;
    }
    private static Bid getBid2() {
        Bid bid2 = new Bid();
        bid2.setId(2);
        bid2.setBiddingPrice(120);
        bid2.setProductId(101);
        bid2.setTimestamp(Timestamp.from(Instant.now()));
        return bid2;
    }

    private static Bid getBid3() {
        Bid bid3 = new Bid();
        bid3.setId(3);
        bid3.setProductId(102);
        bid3.setBiddingPrice(120);
        bid3.setTimestamp(Timestamp.from(Instant.now()));
        return bid3;
    }

    private static Bid getBid4() {
        Bid bid4 = new Bid();
        bid4.setId(4);
        bid4.setProductId(101);
        bid4.setBiddingPrice(120);
        bid4.setTimestamp(Timestamp.from(Instant.now()));
        return bid4;
    }

    @Test
    public void getFirstHighestBidTest(){

        Bid response = auctionRepository.findHighestBiddingPriceByProductId(Long.valueOf(101));
        assertEquals(2,response.getId(),"First highest bid corresponding to product id must be fetched.");
    }
}
