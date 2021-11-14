package com.lowry.target.casestudy.businesslogic.impl;

import com.lowry.target.casestudy.businesslogic.ProductBL;
import com.lowry.target.casestudy.dataaccess.ProductRepository;
import com.lowry.target.casestudy.persistence.ProductEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;

public class ProductBLImpl implements ProductBL {

    private final ProductRepository productRepository;

    @Autowired
    public ProductBLImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductEntity getProductByProductId(String productId) {
        validateInput(productId);
        return productRepository.findByProductId(productId);
    }

    @Override
    public ProductEntity updateProductCostByProductId(ProductEntity productEntity) {
        return productRepository.save(productEntity);
    }

    private void validateInput(String productId) {
        if (ObjectUtils.isEmpty(productId)) {
            throw new IllegalArgumentException("Product ID must not be null or blank");
        }
    }
}
