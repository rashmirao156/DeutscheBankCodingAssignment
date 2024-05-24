package com.db.auction.controller;

import com.db.auction.model.Bid;
import com.db.auction.service.AuctionService;
import com.db.auction.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuctionController {

    @Autowired
    AuctionService auctionService;

    @Autowired
    ProductService productService;

    @PostMapping("auction/bid")
    private ResponseEntity<HttpStatus> submitBid(@RequestBody Bid bid) {
        auctionService.submitBid(bid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("auction/end/{productId}")
    private Bid endBid(@PathVariable long productId) {
        productService.UpdateProductStatus(productId);
        return auctionService.getHighestBid(productId);
    }
}
