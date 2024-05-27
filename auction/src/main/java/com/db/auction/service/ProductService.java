package com.db.auction.service;

import com.db.auction.enums.AuctionStatus;
import com.db.auction.exception.ProductServiceException;
import com.db.auction.model.Product;
import com.db.auction.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * service class for product.
 */
@Component
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    /**
     * register a new product for auction, set status as ONGOING.
     *
     * @param product product
     * @return saved product.
     */
    public Product registerProduct(Product product) {
        try {
            product.setStatus(AuctionStatus.ONGOING);
            return productRepository.save(product);
        } catch (Exception e) {
            throw new ProductServiceException(e.getMessage(), e);
        }
    }

    /**
     * get all products for which auction is ONGOING.
     *
     * @return list of products.
     */
    public List<Product> getProducts() {
        try {

            final List<Product> products = productRepository.findAll();
            // Filter products with status as "ongoing"
            return products.stream()
                    .filter(product -> AuctionStatus.ONGOING.equals(product.getStatus()))
                    .toList();
        } catch (Exception e) {
            throw new ProductServiceException(e.getMessage(), e);
        }
    }

    /**
     * update product status as COMPLETED when auction ends.
     *
     * @param productId id of the product.
     */
    public Long UpdateProductStatus(Long productId) {
        try {
            Product product = productRepository.getReferenceById(productId);
            product.setStatus(AuctionStatus.COMPLETED);
             productRepository.save(product);
             return productId;
        } catch (Exception e) {
            throw new ProductServiceException(e.getMessage(), e);
        }
    }
}
