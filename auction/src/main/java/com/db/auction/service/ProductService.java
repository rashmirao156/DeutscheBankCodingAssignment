package com.db.auction.service;

import com.db.auction.enums.AuctionStatus;
import com.db.auction.model.Bid;
import com.db.auction.model.Product;
import com.db.auction.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    public Product registerProduct(Product user) {
        return  productRepository.save(user);
    }

    public List<Product> getProducts() {
        return  productRepository.findAll();
    }

    public void UpdateProductStatus(Long productId) {
        Product product =  productRepository.getReferenceById(productId);
        product.setStatus(AuctionStatus.COMPLETED);
        productRepository.save(product);
    }
}
