package com.db.auction.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Data
@Getter
@Setter
@Entity
@Table(name = "bid")
public class Bid {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "productId")
    private long productId;
    @Column(name = "biddingPrice")
    private double biddingPrice;
    @Column(name = "buyerId")
    private long buyerId;
    @Column(name = "sellerId")
    private long sellerId;
    @Column(name = "updatedAt")
    private Timestamp timestamp;
}
