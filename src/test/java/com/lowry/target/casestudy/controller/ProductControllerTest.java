package com.lowry.target.casestudy.controller;

import com.google.gson.Gson;
import com.lowry.target.casestudy.businesslogic.ProductBL;
import com.lowry.target.casestudy.config.WebConfiguration;
import com.lowry.target.casestudy.data.ResponseDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.web.reactive.function.client.WebClient;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Import(WebConfiguration.class)
public class ProductControllerTest {

    @Autowired
    WebClient webClient;

    @Autowired
    ProductBL productBL;

    @Autowired
    ProductController productController = new ProductController(webClient, productBL);

    @Test
    public void testGetter() {

        String jsonString = "{\"data\":{\"product\":{\"tcin\":\"13860428\",\"item\":{\"product_description\":{\"title\":\"The Big Lebowski (Blu-ray)\",\"downstream_description\":\"Jeff \\\"The Dude\\\" Lebowski (Bridges) is the victim of mistaken identity. Thugs break into his apartment in the errant belief that they are accosting Jeff Lebowski, the eccentric millionaire philanthropist, not the laid-back, unemployed Jeff Lebowski. In the aftermath, \\\"The Dude\\\" seeks restitution from his wealthy namesake. He and his buddies (Goodman and Buscemi) are swept up in a kidnapping plot that quickly spins out of control.\"},\"enrichment\":{\"images\":{\"primary_image_url\":\"https://target.scene7.com/is/image/Target/GUEST_bac49778-a5c7-4914-8fbe-96e9cd549450\"}},\"product_classification\":{\"product_type_name\":\"ELECTRONICS\",\"merchandise_type_name\":\"Movies\"},\"primary_brand\":{\"name\":\"Universal Home Video\"}}}}}";
        Gson gson = new Gson();
        ResponseDTO data = gson.fromJson(jsonString, ResponseDTO.class);

        assertEquals(data.getData().getProduct().getTcin(), "13860428");
    }

    @Test
    public void testGetProductInformationById() {
        productController.getProductInformationByID("13860428");

    }


}
