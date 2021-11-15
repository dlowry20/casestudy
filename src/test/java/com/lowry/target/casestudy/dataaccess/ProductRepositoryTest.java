package com.lowry.target.casestudy.dataaccess;

import com.lowry.target.casestudy.persistence.ProductEntity;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.test.StepVerifier;

@DataMongoTest
@RunWith(SpringRunner.class)
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Before
    public void before() {
        productRepository.deleteAll().block();
    }

    @Test
    public void testFindByProductId() {
        String productId = "1234";
        ProductEntity product = new ProductEntity(productId, 12.23, "USD");
        productRepository.save(product).block();
        // 5 because includes the initial input by the CommandLineRunner bean
        StepVerifier.create(productRepository.findAll())
                .expectNextCount(1)
                .verifyComplete();
        StepVerifier.create(productRepository.findByProductId(productId))
                .expectNextMatches(productEntity -> productId.equalsIgnoreCase(productEntity.getProductId()))
                .verifyComplete();
    }

    @Test
    public void testUpdate() {
        String productId = "1234";
        ProductEntity product = new ProductEntity(productId, 12.23, "USD");
        StepVerifier.create(productRepository.save(product))
                .expectNext(product)
                .verifyComplete();

        StepVerifier.create(productRepository.findByProductId(productId))
                .expectNextMatches(productReturned -> productReturned.getProductId().equalsIgnoreCase(productId))
                .verifyComplete();
        double newPrice = 5.10;
        ProductEntity updatedPrice = new ProductEntity(product.getProductId(), newPrice, product.getCurrencyCode());

        StepVerifier.create(productRepository.save(updatedPrice))
                .expectNextMatches(entityWithUpdatedPrice -> entityWithUpdatedPrice.getPrice() == newPrice)
                .verifyComplete();
    }
}
