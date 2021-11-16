package com.lowry.target.casestudy.controller;

import com.lowry.target.casestudy.businesslogic.ProductBL;
import com.lowry.target.casestudy.config.WebConfiguration;
import com.lowry.target.casestudy.data.ResponseDTO;
import com.lowry.target.casestudy.data.response.ProductPrice;
import com.lowry.target.casestudy.data.response.ProductResponse;
import com.lowry.target.casestudy.persistence.ProductEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;

@RestController("/product")
@Import(WebConfiguration.class)
public class ProductController {

    private final WebClient webClient;
    private final ProductBL productBL;

    @Autowired
    public ProductController(WebClient webClient, ProductBL productBL) {
        this.webClient = webClient;
        this.productBL = productBL;
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Mono<ProductResponse>> getProductInformationByID(@PathVariable String id) {
        validateInput(id);
        Mono<ProductResponse> productResponse = Mono.zip(
                getProductMono(id).switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Product does not exist in local DB."))),
                getResponseMono(id).switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Product does not exist in Remote API."))),
                this::buildProductResponse
        );
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

    @PostMapping("/products/{id}")
    public ResponseEntity<Mono<ProductResponse>> updateProductCostById(@RequestBody ProductResponse productUpdatedPrice, @PathVariable String id) {
        validateInput(id);
        return new ResponseEntity<>(
                productBL.updateProductCostByProductId(id, productUpdatedPrice.getCurrent_price().getValue(), productUpdatedPrice.getCurrent_price().getCurrency_code())
                        .map(entity -> new ProductResponse(
                                entity.getProductId(),
                                productUpdatedPrice.getName(),
                                new ProductPrice(entity.getPrice(), entity.getCurrencyCode())))
                        .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Product does not exist in local DB."))),
                HttpStatus.OK);
    }

    public Mono<ResponseDTO> getResponseMono(String id) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.queryParam("tcin",id).build())
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, ClientResponse::createException)
                .bodyToMono(ResponseDTO.class)
                .retryWhen(Retry.fixedDelay(2, Duration.ofMillis(100)));
    }

    private Mono<ProductEntity> getProductMono(String productId) {
        return productBL.getProductByProductId(productId);
    }

    private ProductResponse buildProductResponse(ProductEntity product, ResponseDTO responseDTO) {
        ProductPrice productPrice = new ProductPrice(product.getPrice(), product.getCurrencyCode());
        return new ProductResponse(responseDTO.getProductId(), responseDTO.getProductTitle(), productPrice);
    }

    private void validateInput(String productID) {
        if (productID == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product ID is a required field");
        }

        try {
            Integer.parseInt(productID);
        } catch (NumberFormatException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product ID must be a number");
        }
    }
}
