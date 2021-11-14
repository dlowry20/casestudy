package com.lowry.target.casestudy.businesslogic;

import com.lowry.target.casestudy.businesslogic.impl.ProductBLImpl;
import com.lowry.target.casestudy.dataaccess.ProductRepository;
import com.lowry.target.casestudy.persistence.ProductEntity;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ProductBLTest {

    ProductRepository productRepository = mock(ProductRepository.class);
    ProductBL productBL = new ProductBLImpl(productRepository);

    @Test
    public void testRead() {
        String productID = "123";
        ProductEntity testProductEntity = new ProductEntity(productID, 12.12, "USD");
        when(productRepository.findByProductId(productID)).thenReturn(testProductEntity);
        ProductEntity returnedProductEntity = productBL.getProductByProductId(productID);

        assertEquals(returnedProductEntity.getProductId(), productID);
        assertEquals(returnedProductEntity.getCurrencyCode(), testProductEntity.getCurrencyCode());
        assertEquals(returnedProductEntity.getPrice(), testProductEntity.getPrice(), 0.0);
    }

    @Test
    public void testUpdate() {
        String productID = "123";
        ProductEntity testProductEntity = new ProductEntity(productID, 12.12, "USD");
        when(productRepository.save(testProductEntity)).thenReturn(testProductEntity);
        ProductEntity returnedProductEntity = productBL.updateProductCostByProductId(testProductEntity);

        assertEquals(returnedProductEntity.getProductId(), productID);
        assertEquals(returnedProductEntity.getCurrencyCode(), testProductEntity.getCurrencyCode());
        assertEquals(returnedProductEntity.getPrice(), testProductEntity.getPrice(), 0.0);
    }

}
