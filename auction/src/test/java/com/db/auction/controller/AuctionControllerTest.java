package com.db.auction.controller;

import com.db.auction.client.TokenClient;
import com.db.auction.model.Bid;
import com.db.auction.service.AuctionService;
import com.db.auction.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class AuctionControllerTest {
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext applicationContext;
    @MockBean
    AuctionService auctionService;

    @MockBean
    ProductService productService;

    @MockBean
    TokenClient tokenClient;

    @BeforeEach
    public void init(){
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(applicationContext).apply(springSecurity())
                .build();
    }

    @Test
    public void testSubmitBidAsSeller() throws Exception {
        when(tokenClient.validateToken(any())).thenReturn("SELLER");
        when(auctionService.submitBid(any())).thenReturn(getBid());
        mockMvc.perform(MockMvcRequestBuilders.post("/auction/bid")
                        .header("Authorization", "Bearer sampleToken")

                        .contentType("application/json")
                        .content(getBiddingRequest()))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testSubmitBidAsBuyer() throws Exception {
        when(tokenClient.validateToken(any())).thenReturn("BUYER");
        when(auctionService.submitBid(any())).thenReturn(getBid());
        mockMvc.perform(MockMvcRequestBuilders.post("/auction/bid")
                        .header("Authorization", "Bearer sampleToken")

                        .contentType("application/json")
                        .content(getBiddingRequest()))
                .andExpect(status().isOk());
    }

    @Test
    public void testEndAuctionAsBuyer() throws Exception {
        when(tokenClient.validateToken(any())).thenReturn("BUYER");
        when(productService.UpdateProductStatus(any())).thenReturn(Long.valueOf(1));
        when(auctionService.getHighestBid(any())).thenReturn(getBid());
        mockMvc.perform(MockMvcRequestBuilders.post("/auction/end/1")
                        .header("Authorization", "Bearer sampleToken")
                        .contentType("application/json")
                        .content(getBiddingRequest()))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testEndAuctionAsSeller() throws Exception {
        when(tokenClient.validateToken(any())).thenReturn("BUYER");
        when(productService.UpdateProductStatus(any())).thenReturn(Long.valueOf(1));
        when(auctionService.getHighestBid(any())).thenReturn(getBid());
        mockMvc.perform(MockMvcRequestBuilders.post("/auction/end/1")
                        .header("Authorization", "Bearer sampleToken")
                        .contentType("application/json")
                        .content(getBiddingRequest()))
                .andExpect(status().isForbidden());
    }

    private Bid getBid(){
       return new Bid();
    }

    private  String getBiddingRequest(){
       return "{    \"id\":\"103\",\n" +
                "    \"productId\":\"101\",\n" +
                "    \"biddingPrice\":\"110.0\",\n" +
                "    \"buyerId\":\"2\",\n" +
                "    \"sellerId\":\"1\"\n" +
                "}";
    }
}
