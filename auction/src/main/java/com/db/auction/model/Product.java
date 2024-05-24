package com.db.auction.model;

import com.db.auction.enums.AuctionStatus;
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
@Table(name = "product")
public class Product {
    @Id
    private long id;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "minimumPrice")
    private double minimumPrice;
    @Column(name = "status")
    private AuctionStatus status;


}
