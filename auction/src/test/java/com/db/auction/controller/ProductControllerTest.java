package com.db.auction.controller;

import com.db.auction.client.TokenClient;
import com.db.auction.enums.AuctionStatus;
import com.db.auction.model.Product;
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

import java.util.ArrayList;
import java.util.List;

import static com.db.auction.constants.TestConstants.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class ProductControllerTest {


    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext applicationContext;
    @MockBean
    ProductService productService;


    @MockBean
    TokenClient tokenClient;

    @BeforeEach
    public void init() {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(applicationContext).apply(springSecurity())
                .build();
    }


    @Test
    public void testRegisterWithSellerRole() throws Exception {
        when(tokenClient.validateToken(any())).thenReturn(SELLER);
        when(productService.registerProduct(any())).thenReturn(getProduct());
        String productJson = getRequest();
        mockMvc.perform(MockMvcRequestBuilders.post("/product/register")
                        .header(AUTHORIZATION, BEARER_SAMPLE_TOKEN)

                        .contentType(CONTENT_TYPE)
                        .content(productJson))
                .andExpect(status().isOk());
    }

    @Test
    public void testRegisterWithBuyerRole() throws Exception {
        when(tokenClient.validateToken(any())).thenReturn(BUYER);
        when(productService.registerProduct(any())).thenReturn(getProduct());
        String productJson = getRequest();
        mockMvc.perform(MockMvcRequestBuilders.post("/product/register")
                        .header(AUTHORIZATION, BEARER_SAMPLE_TOKEN)

                        .contentType(CONTENT_TYPE)
                        .content(productJson))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testGetAllProductsWithSeller() throws Exception {
        when(tokenClient.validateToken(any())).thenReturn(SELLER);
        when(productService.getProducts()).thenReturn(getProductList());
        String productJson = getRequest();
        mockMvc.perform(MockMvcRequestBuilders.get("/product/all")
                        .header(AUTHORIZATION, BEARER_SAMPLE_TOKEN)
                )
                .andExpect(status().isForbidden());
    }

    @Test
    public void testGetAllProductsWithBuyer() throws Exception {
        when(tokenClient.validateToken(any())).thenReturn(BUYER);
        when(productService.getProducts()).thenReturn(getProductList());
        String productJson = getRequest();
        mockMvc.perform(MockMvcRequestBuilders.get("/product/all")
                        .header(AUTHORIZATION, BEARER_SAMPLE_TOKEN)
                )
                .andExpect(status().isOk());
    }

    private static String getRequest() {
        return "{\"name\":\"Sample Product\", \"minimumPrice\": 100.0}";
    }

    private Product getProduct() {
        Product product = new Product();
        product.setId(1);
        product.setStatus(AuctionStatus.ONGOING);
        product.setName("airpods");
        product.setMinimumPrice(100.0);
        return product;
    }

    private List<Product> getProductList() {
        List<Product> productList = new ArrayList<>();
        productList.add(getProduct());
        return productList;
    }

}
