package com.lowry.target.casestudy.businesslogic;

import com.lowry.target.casestudy.businesslogic.impl.ProductBLImpl;
import com.lowry.target.casestudy.dataaccess.ProductRepository;
import com.lowry.target.casestudy.persistence.ProductEntity;
import org.junit.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ProductBLTest {

    ProductRepository productRepository = mock(ProductRepository.class);
    ProductBL productBL = new ProductBLImpl(productRepository);

    @Test
    public void testRead() {
        String productID = "123";
        ProductEntity testProductEntity = new ProductEntity(productID, 12.12, "USD");
        when(productRepository.findByProductId(productID)).thenReturn(Mono.just(testProductEntity));
        Mono<ProductEntity> returnedProductEntity = productBL.getProductByProductId(productID);

        StepVerifier.create(returnedProductEntity)
                        .expectNext(testProductEntity)
                        .verifyComplete();
    }

    @Test
    public void badInput() {
        String productID = "";

        Exception ex = assertThrows(RuntimeException.class, () -> productBL.getProductByProductId(productID));
        String expectedError = "Product ID must not be null or blank";
        assertTrue(ex.getMessage().contains(expectedError));
    }

    @Test
    public void testUpdate() {
        String productID = "123";
        ProductEntity testProductEntity = new ProductEntity(productID, 12.12, "USD");
        when(productRepository.save(testProductEntity)).thenReturn(Mono.just(testProductEntity));
        when(productRepository.findByProductId(productID)).thenReturn(Mono.just(testProductEntity));
        Mono<ProductEntity> returnedProductMono = productBL.updateProductCostByProductId(testProductEntity.getProductId(),
                testProductEntity.getPrice(), testProductEntity.getCurrencyCode());

        StepVerifier.create(returnedProductMono)
                .expectNext(testProductEntity)
                .verifyComplete();
    }
}
