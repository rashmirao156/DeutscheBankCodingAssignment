package com.db.auction.controller;

import com.db.auction.exception.ProductServiceException;
import com.db.auction.model.Bid;
import com.db.auction.model.Product;
import com.db.auction.service.AuctionService;
import com.db.auction.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class AuctionController {

    @Autowired
    AuctionService auctionService;

    @Autowired
    ProductService productService;

    /**
     * API for buyer to submit bid.
     *
     * @param bid bid.
     * @return message.
     */
    @PostMapping("auction/bid")
    private ResponseEntity<String> submitBid(@RequestBody Bid bid) {
        Optional<Product> product = productService.getProductById(bid.getProductId());
        if(!product.isPresent()){
            throw new ProductServiceException("Invalid product");
        }
        final double minPrice = product.get().getMinimumPrice();
        if(bid.getBiddingPrice()<minPrice){
            throw new ProductServiceException(String.format("Bid Rejected,minimum bidding price is %s",minPrice));
        }
        Bid response = auctionService.submitBid(bid);
        return ResponseEntity.status(HttpStatus.OK)
                .body(String.format("successfully submitted bid with id %s", response.getId()));
    }

    /**
     * API used by seller to end the auction.
     * Updates the product status as Completed.
     * Fetches the highest Bid for product.
     *
     * @param productId product Id.
     * @return Highest bid.
     */
    @PostMapping("auction/end/{productId}")
    private Bid endBid(@PathVariable long productId) {
        productService.UpdateProductStatus(productId);
        return auctionService.getHighestBid(productId);
    }
}
