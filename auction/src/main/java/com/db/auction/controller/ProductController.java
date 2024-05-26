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

    @PostMapping("product/register")
    private ResponseEntity<HttpStatus> registerProduct(@RequestBody Product product) {
        productService.registerProduct(product);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/product/all")
    private List<Product> getProducts() {
        return productService.getProducts();
    }
}
