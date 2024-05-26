package com.db.auction.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Component
public class TokenClient {

    @Autowired
    private RestTemplate restTemplate;

    public String validateToken(String token) {
        String userAuthServiceUrl = "http://localhost:8080/user/validate" ;
        return restTemplate.postForObject(userAuthServiceUrl, token, String.class);
    }
}
