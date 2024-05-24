package com.db.auction.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Entity
@Table(name ="bid")
public class Bid
{
    @Id
    private long id;
    @Column(name = "productId")
    private long productId;
    @Column(name = "biddingPrice")
    private double biddingPrice;
    @Column(name = "buyerId")
    private long buyerId;
    @Column(name = "sellerId")
    private long sellerId;

}
