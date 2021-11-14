package com.lowry.target.casestudy.persistence;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Document("productentities")
public class ProductEntity {

    @Id
    private String productId;

    @Setter
    private double price;
    private String currencyCode;

    public ProductEntity(String productId, double price, String currencyCode) {
        this.productId = productId;
        this.price = price;
        this.currencyCode = currencyCode;
    }

}
