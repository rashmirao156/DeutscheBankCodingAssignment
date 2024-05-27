package com.db.auction.repository;

import com.db.auction.model.Bid;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

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

    }

    private static Bid getBid2() {
        Bid bid2 = new Bid();
        bid2.setBiddingPrice(120);
        bid2.setProductId(101);
        return bid2;
    }

    private static Bid getBid1() {
        Bid bid1 = new Bid();
        bid1.setProductId(101);
        bid1.setBiddingPrice(100);
        return bid1;
    }

    private static Bid getBid3() {
        Bid bid1 = new Bid();
        bid1.setProductId(102);
        bid1.setBiddingPrice(140);
        return bid1;
    }

    @Test
    public void getHighestBidTest(){

        Bid response = auctionRepository.findHighestBiddingPriceByProductId(Long.valueOf(101));
        assertEquals(120,response.getBiddingPrice(),"highest bid corresponding to product id must be fetched.");
    }
}
