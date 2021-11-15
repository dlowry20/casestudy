package com.lowry.target.casestudy.config;

import com.lowry.target.casestudy.dataaccess.ProductRepository;
import com.lowry.target.casestudy.persistence.ProductEntity;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

@EnableReactiveMongoRepositories(basePackageClasses = ProductRepository.class)
@Configuration
public class AppStartupConfiguration {

    @Bean
    CommandLineRunner loadDatabaseCommandLineRunner(ProductRepository productRepository) {
        List<ProductEntity> productEntities = Arrays.asList(
                new ProductEntity("13860428", 13.49, "USD"),
                new ProductEntity("54456119", 3.29, "USD"),
                new ProductEntity("13264003", 3.49, "USD"),
                new ProductEntity("12954218", 1.49, "USD")

        );
        return args -> {

            productRepository.deleteAll().block();
            productEntities.forEach(productEntity -> productRepository.save(productEntity).block());
            System.out.println("ADDING RECORDS");
        };
    }
}
