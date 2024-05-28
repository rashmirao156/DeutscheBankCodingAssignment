package com.db.auction.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AuctionServiceException extends RuntimeException {

    public AuctionServiceException(String message, Exception e) {
        super(message, e);

    }
}

