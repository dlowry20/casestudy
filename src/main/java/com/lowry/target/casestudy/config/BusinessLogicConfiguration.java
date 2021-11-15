package com.lowry.target.casestudy.config;

import com.lowry.target.casestudy.businesslogic.ProductBL;
import com.lowry.target.casestudy.businesslogic.impl.ProductBLImpl;
import com.lowry.target.casestudy.dataaccess.ProductRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BusinessLogicConfiguration {

    @Bean
    ProductBL productBL(ProductRepository productRepository) {
        return new ProductBLImpl(productRepository);
    }
}
