package com.lowry.target.casestudy.dataaccess;

import com.lowry.target.casestudy.persistence.ProductEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<ProductEntity, String> {

    ProductEntity findByProductId(String productId);
}
