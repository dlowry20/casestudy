package com.lowry.target.casestudy.businesslogic;

import com.lowry.target.casestudy.persistence.ProductEntity;
import reactor.core.publisher.Mono;

public interface ProductBL {

    Mono<ProductEntity> getProductByProductId(String productId);

    Mono<ProductEntity> updateProductCostByProductId(String productId, double newPrice, String newCurrencyCode);
}
