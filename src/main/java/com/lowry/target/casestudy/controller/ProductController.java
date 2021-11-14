package com.lowry.target.casestudy.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lowry.target.casestudy.businesslogic.ProductBL;
import com.lowry.target.casestudy.config.WebConfiguration;
import com.lowry.target.casestudy.data.Response;
import com.lowry.target.casestudy.data.response.ProductPrice;
import com.lowry.target.casestudy.data.response.ProductResponse;
import com.lowry.target.casestudy.persistence.ProductEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@RestController("/product")
@Import(WebConfiguration.class)
public class ProductController {

    private final WebClient webClient;
    private final ProductBL productBL;
    private final ObjectMapper objectMapper;

    @Autowired
    public ProductController(WebClient webClient, ProductBL productBL) {
        this.webClient = webClient;
        this.productBL = productBL;
        this.objectMapper = new ObjectMapper();
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<ProductResponse> getProductInformationByID(@PathVariable String id) {
        validateInput(id);
        Response response = getResponse(id);
        ProductEntity productEntity = getProduct(id);
        ResponseEntity<ProductResponse> productResponseResponseEntity;
        if (response == null || productEntity == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product does not exist.");
        } else {
            ProductResponse productResponse = buildProductResponse(productEntity, response);
            productResponseResponseEntity = new ResponseEntity<>(productResponse, HttpStatus.OK);
        }
        return productResponseResponseEntity;
    }

    @PostMapping("/products/{id}")
    public ResponseEntity<ProductResponse> updateProductCostById(@RequestBody ProductResponse productUpdatedPrice, @PathVariable String id) {
        validateInput(id);
        ProductEntity productEntity = getProduct(id);
        productEntity.setPrice(productUpdatedPrice.getCurrent_price().getValue());
        productBL.updateProductCostByProductId(productEntity);
        return new ResponseEntity<>(productUpdatedPrice, HttpStatus.OK);
    }

    // TODO : Make Async
    private Response getResponse(String id) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.queryParam("tcin",id).build())
                .retrieve()
                .bodyToMono(Response.class)
                .onErrorResume(WebClientResponseException.class, ex -> ex.getRawStatusCode() == 404 ? Mono.empty() : Mono.error(ex))
                .block();
    }

    // TODO : Make Async
    private ProductEntity getProduct(String productId) {
        return productBL.getProductByProductId(productId);
    }

    private ProductResponse buildProductResponse(ProductEntity product, Response response) {
        ProductPrice productPrice = new ProductPrice(product.getPrice(), product.getCurrencyCode());
        return new ProductResponse(response.getProductId(), response.getProductTitle(), productPrice);
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
