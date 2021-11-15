package com.lowry.target.casestudy.dataaccess;

import com.lowry.target.casestudy.persistence.ProductEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface ReactiveProductRepository extends ReactiveMongoRepository<ProductEntity, String> {

    Mono<ProductEntity> findByProductId(String productId);
}
