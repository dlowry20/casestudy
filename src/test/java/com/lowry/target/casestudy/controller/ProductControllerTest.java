package com.lowry.target.casestudy.controller;

import com.google.gson.Gson;
import com.lowry.target.casestudy.businesslogic.ProductBL;
import com.lowry.target.casestudy.config.WebConfiguration;
import com.lowry.target.casestudy.data.ResponseDTO;
import com.lowry.target.casestudy.data.response.ProductResponse;
import com.lowry.target.casestudy.persistence.ProductEntity;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Import(WebConfiguration.class)
public class ProductControllerTest {

    private final ExchangeFunction mockExchangeFunction = mock(ExchangeFunction.class);
    private final WebClient webClient = WebClient.builder()
            .exchangeFunction(mockExchangeFunction)
            .build();
    private final ProductBL mockProductBL = mock(ProductBL.class);
    ProductController productController = new ProductController(webClient, mockProductBL);

    @Test
    public void testGetter() {

        String jsonString = "{\"data\":{\"product\":{\"tcin\":\"13860428\",\"item\":{\"product_description\":{\"title\":\"The Big Lebowski (Blu-ray)\",\"downstream_description\":\"Jeff \\\"The Dude\\\" Lebowski (Bridges) is the victim of mistaken identity. Thugs break into his apartment in the errant belief that they are accosting Jeff Lebowski, the eccentric millionaire philanthropist, not the laid-back, unemployed Jeff Lebowski. In the aftermath, \\\"The Dude\\\" seeks restitution from his wealthy namesake. He and his buddies (Goodman and Buscemi) are swept up in a kidnapping plot that quickly spins out of control.\"},\"enrichment\":{\"images\":{\"primary_image_url\":\"https://target.scene7.com/is/image/Target/GUEST_bac49778-a5c7-4914-8fbe-96e9cd549450\"}},\"product_classification\":{\"product_type_name\":\"ELECTRONICS\",\"merchandise_type_name\":\"Movies\"},\"primary_brand\":{\"name\":\"Universal Home Video\"}}}}}";
        Gson gson = new Gson();
        ResponseDTO data = gson.fromJson(jsonString, ResponseDTO.class);

        assertEquals(data.getData().getProduct().getTcin(), "13860428");
    }

    @Test
    public void testGetProductInformationById() {
        String productId = "13860428";
        String jsonResponseString = "{\"data\":{\"product\":{\"tcin\":\"13860428\",\"item\":{\"product_description\":{\"title\":\"The Big Lebowski (Blu-ray)\",\"downstream_description\":\"Jeff \\\"The Dude\\\" Lebowski (Bridges) is the victim of mistaken identity. Thugs break into his apartment in the errant belief that they are accosting Jeff Lebowski, the eccentric millionaire philanthropist, not the laid-back, unemployed Jeff Lebowski. In the aftermath, \\\"The Dude\\\" seeks restitution from his wealthy namesake. He and his buddies (Goodman and Buscemi) are swept up in a kidnapping plot that quickly spins out of control.\"},\"enrichment\":{\"images\":{\"primary_image_url\":\"https://target.scene7.com/is/image/Target/GUEST_bac49778-a5c7-4914-8fbe-96e9cd549450\"}},\"product_classification\":{\"product_type_name\":\"ELECTRONICS\",\"merchandise_type_name\":\"Movies\"},\"primary_brand\":{\"name\":\"Universal Home Video\"}}}}}";
        Gson gson = new Gson();
        ProductEntity testProductEntity = new ProductEntity(productId, 12.12, "USD");

        when(mockExchangeFunction.exchange(any(ClientRequest.class))).thenReturn(Mono.just(mockClientResponse(jsonResponseString)));
        when(mockProductBL.getProductByProductId(productId)).thenReturn(Mono.just(testProductEntity));

        ResponseEntity<Mono<ProductResponse>> response = productController.getProductInformationByID("13860428");
        StepVerifier.create(Objects.requireNonNull(response.getBody()))
                .expectNextMatches(productResponse -> productResponse.getCurrent_price().getValue() == 12.12)
                .verifyComplete();
    }

    @Test
    public void dataBaseDoesNotHaveTheProduct() {
        String productId = "13860428";
        String jsonResponseString = "{\"data\":{\"product\":{\"tcin\":\"13860428\",\"item\":{\"product_description\":{\"title\":\"The Big Lebowski (Blu-ray)\",\"downstream_description\":\"Jeff \\\"The Dude\\\" Lebowski (Bridges) is the victim of mistaken identity. Thugs break into his apartment in the errant belief that they are accosting Jeff Lebowski, the eccentric millionaire philanthropist, not the laid-back, unemployed Jeff Lebowski. In the aftermath, \\\"The Dude\\\" seeks restitution from his wealthy namesake. He and his buddies (Goodman and Buscemi) are swept up in a kidnapping plot that quickly spins out of control.\"},\"enrichment\":{\"images\":{\"primary_image_url\":\"https://target.scene7.com/is/image/Target/GUEST_bac49778-a5c7-4914-8fbe-96e9cd549450\"}},\"product_classification\":{\"product_type_name\":\"ELECTRONICS\",\"merchandise_type_name\":\"Movies\"},\"primary_brand\":{\"name\":\"Universal Home Video\"}}}}}";

        when(mockExchangeFunction.exchange(any(ClientRequest.class))).thenReturn(Mono.just(mockClientResponse(jsonResponseString)));
        when(mockProductBL.getProductByProductId(productId)).thenReturn(Mono.empty());

        ResponseEntity<Mono<ProductResponse>> response = productController.getProductInformationByID("13860428");

        StepVerifier.create(Objects.requireNonNull(response.getBody()))
                .expectError(ResponseStatusException.class)
                .verify();

    }

    @Test
    public void externalApiDoesNotHaveTheProduct() {
        String productId = "13860428";
        ProductEntity testProductEntity = new ProductEntity(productId, 12.12, "USD");

        when(mockExchangeFunction.exchange(any(ClientRequest.class))).thenReturn(Mono.just(mockClientResponse("")));
        when(mockProductBL.getProductByProductId(productId)).thenReturn(Mono.just(testProductEntity));

        ResponseEntity<Mono<ProductResponse>> response = productController.getProductInformationByID("13860428");
        StepVerifier.create(Objects.requireNonNull(response.getBody()))
                .expectError()
                .verify();
    }

    private ClientResponse mockClientResponse(String responseBody) {
        return ClientResponse.create(HttpStatus.OK)
                .header("content-type", "application/json")
                .body(responseBody)
                .build();
    }


}
