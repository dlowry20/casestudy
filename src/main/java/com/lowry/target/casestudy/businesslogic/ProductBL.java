package com.lowry.target.casestudy.businesslogic;

import com.lowry.target.casestudy.persistence.ProductEntity;
import reactor.core.publisher.Mono;

public interface ProductBL {

    ProductEntity getProductByProductId(String productId);

    Mono<ProductEntity> getProductByProductId_Async(String productId);

    ProductEntity updateProductCostByProductId(ProductEntity productEntity);
    Mono<ProductEntity> updateProductCostByProductId_async(ProductEntity productEntity);
}
