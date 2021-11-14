package com.lowry.target.casestudy.dataaccess;

import com.lowry.target.casestudy.persistence.ProductEntity;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@DataMongoTest
@RunWith(SpringRunner.class)
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Before
    public void before() {
        productRepository.deleteAll();
    }

    @Test
    public void testFindByProductId() {
        String productId = "1234";
        ProductEntity product = new ProductEntity(productId, 12.23, "USD");
        productRepository.save(product);

        assertEquals(productRepository.findAll().size(), 1);

        ProductEntity readProduct = productRepository.findByProductId(productId);

        assertNotNull(readProduct);
        assertEquals(product.getProductId(), readProduct.getProductId());
        assertEquals(product.getPrice(), readProduct.getPrice(), 0.0);
        assertEquals(product.getCurrencyCode(), readProduct.getCurrencyCode());
    }

    @Test
    public void testUpdate() {
        String productId = "1234";
        ProductEntity product = new ProductEntity(productId, 12.23, "USD");
        productRepository.save(product);
        assertEquals(productRepository.findAll().size(), 1);
        ProductEntity readProduct = productRepository.findByProductId(productId);

        ProductEntity updatedPrice = new ProductEntity(readProduct.getProductId(), 5.10, readProduct.getCurrencyCode());

        productRepository.save(updatedPrice);

        ProductEntity readAfterUpdate = productRepository.findByProductId(productId);

        assertNotNull(readAfterUpdate);
        assertEquals(updatedPrice.getPrice(), readAfterUpdate.getPrice(), 0.0);
    }
}
