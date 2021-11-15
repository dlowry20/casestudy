package com.lowry.target.casestudy.businesslogic.impl;

import com.lowry.target.casestudy.businesslogic.ProductBL;
import com.lowry.target.casestudy.dataaccess.ProductRepository;
import com.lowry.target.casestudy.dataaccess.ReactiveProductRepository;
import com.lowry.target.casestudy.errorhandling.ProductNotFoundException;
import com.lowry.target.casestudy.persistence.ProductEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import reactor.core.publisher.Mono;

public class ProductBLImpl implements ProductBL {

    private final ProductRepository productRepository;
    private final ReactiveProductRepository reactiveProductRepository;

    @Autowired
    public ProductBLImpl(ProductRepository productRepository, ReactiveProductRepository reactiveProductRepository) {
        this.productRepository = productRepository;
        this.reactiveProductRepository = reactiveProductRepository;
    }

    @Override
    public ProductEntity getProductByProductId(String productId) {
        validateInput(productId);
        return productRepository.findByProductId(productId);
    }

    @Override
    public Mono<ProductEntity> getProductByProductId_Async(String productId) {
        validateInput(productId);
        return reactiveProductRepository.findByProductId(productId);
    }

    @Override
    public ProductEntity updateProductCostByProductId(ProductEntity productEntity) {
        return productRepository.save(productEntity);
    }

    @Override
    public Mono<ProductEntity> updateProductCostByProductId_async(ProductEntity productEntity) {
        return reactiveProductRepository.save(productEntity);
    }

    private void validateInput(String productId) {
        if (ObjectUtils.isEmpty(productId)) {
            throw new IllegalArgumentException("Product ID must not be null or blank");
        }
    }
}
