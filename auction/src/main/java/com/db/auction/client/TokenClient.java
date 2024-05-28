package com.db.auction.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class TokenClient {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${user.auth.url}")
    private String userAuthServiceUrl;

    /**
     * Call User service to validate the token and return user role.
     *
     * @param token user token
     * @return role.
     */
    public String validateToken(String token) {
        return restTemplate.postForObject(userAuthServiceUrl, token, String.class);
    }
}
