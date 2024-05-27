package com.db.auction.service;

import com.db.auction.model.Bid;
import com.db.auction.repository.AuctionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class AuctionServiceTest {
    @Mock
    private AuctionRepository auctionRepository;

    @InjectMocks
    AuctionService auctionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void submitBidTest() {
        Bid bid = new Bid();
        bid.setBiddingPrice(100.0);
        bid.setId(1);
        bid.setBuyerId(3);
        bid.setSellerId(2);
        bid.setProductId(101);
        when(auctionRepository.save(bid)).thenReturn(bid);
        assertEquals(1, auctionService.submitBid(bid).getId());
    }

    @Test
    public void getHighestBidTest() {
        Bid bid = new Bid();
        bid.setBiddingPrice(100.0);
        bid.setId(1);
        bid.setBuyerId(3);
        bid.setSellerId(2);
        bid.setProductId(101);
        when(auctionRepository.findHighestBiddingPriceByProductId(Long.valueOf(101))).thenReturn(bid);
        assertEquals(1, auctionService.getHighestBid(Long.valueOf(101)).getId());
    }
}
