package com.lowry.target.casestudy.businesslogic;

import com.lowry.target.casestudy.persistence.ProductEntity;

public interface ProductBL {

    ProductEntity getProductByProductId(String productId);

    ProductEntity updateProductCostByProductId(ProductEntity productEntity);
}
