package com.db.auction.service;

import com.db.auction.model.Product;
import com.db.auction.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static com.db.auction.enums.AuctionStatus.ONGOING;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void registerProductTest() {
        Product product = new Product();
        product.setName("airpods");
        product.setMinimumPrice(100.0);
        when(productRepository.save(product)).thenReturn(product);
        assertEquals(productService.registerProduct(product).getName(), "airpods",
                "Product must be saved successfully.");

    }

    @Test
    public void getAllProductsTest() {
        Product product = new Product();
        product.setName("airpods");
        product.setMinimumPrice(100.0);

        Product product2 = new Product();
        product2.setName("airpods pro");
        product2.setMinimumPrice(120.0);
        product2.setStatus(ONGOING);

        List<Product> productList = new ArrayList<>();
        productList.add(product);
        productList.add(product2);

        when(productRepository.findAll()).thenReturn(productList);
        assertEquals(productService.getProducts().size(), 1,
                "Active Product list size must be 1.");

    }

    @Test
    public void updateProductStatusTest() {
        Product product = new Product();
        product.setName("airpods");
        product.setId(101);
        product.setMinimumPrice(100.0);
        when(productRepository.getReferenceById(Long.valueOf(101))).thenReturn(product);
        productService.UpdateProductStatus(Long.valueOf(101));
        verify(productRepository, times(1)).save(any(Product.class));

    }
}
