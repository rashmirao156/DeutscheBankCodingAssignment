package com.db.auction.controller;

import com.db.auction.model.Product;
import com.db.auction.service.AuctionService;
import com.db.auction.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductController {
    @Autowired
    ProductService productService;

    /**
     * API for seller to add a new product for auction and set minimum price.
     * @param product new product for auction.
     * @return message.
     */
    @PostMapping("product/register")
    private ResponseEntity<String> registerProduct(@RequestBody final Product product) {
       final Product response =  productService.registerProduct(product);
        return ResponseEntity.status(HttpStatus.OK)
                .body(String.format("Product added successfully , product id : %s",response.getId()));
    }

    /**
     *  API for Buyer to get all products for which auction is ongoing.
     * @return list of all products.
     */
    @GetMapping("/product/all")
    private List<Product> getProducts() {
        return productService.getProducts();
    }
}
