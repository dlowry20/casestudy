package com.lowry.target.casestudy.businesslogic.impl;

import com.lowry.target.casestudy.businesslogic.ProductBL;
import com.lowry.target.casestudy.dataaccess.ProductRepository;
import com.lowry.target.casestudy.persistence.ProductEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import reactor.core.publisher.Mono;

public class ProductBLImpl implements ProductBL {

    private final ProductRepository productRepository;

    @Autowired
    public ProductBLImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Mono<ProductEntity> getProductByProductId(String productId) {
        validateInput(productId);
        return productRepository.findByProductId(productId);
    }

    @Override
    public Mono<ProductEntity> updateProductCostByProductId(String productId, double newPrice, String newCurrencyCode) {
        return productRepository.findByProductId(productId)
                .map(productEntity -> {
                    productEntity.setPrice(newPrice);
                    productEntity.setCurrencyCode(newCurrencyCode);
                    return productEntity;
                })
                .flatMap(productRepository::save);
    }

    private void validateInput(String productId) {
        if (ObjectUtils.isEmpty(productId)) {
            throw new IllegalArgumentException("Product ID must not be null or blank");
        }
    }
}
