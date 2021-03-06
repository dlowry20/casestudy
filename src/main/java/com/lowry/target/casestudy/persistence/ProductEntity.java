package com.lowry.target.casestudy.persistence;

import com.lowry.target.casestudy.data.response.ProductResponse;
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
    @Setter
    private String currencyCode;

    public ProductEntity(String productId, double price, String currencyCode) {
        this.productId = productId;
        this.price = price;
        this.currencyCode = currencyCode;
    }

    public ProductEntity updatePrice(ProductEntity productEntity, double price, String currencyCode) {
        return new ProductEntity(productEntity.getProductId(), price, currencyCode);
    }

}
