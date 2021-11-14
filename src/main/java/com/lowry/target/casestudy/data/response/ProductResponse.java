package com.lowry.target.casestudy.data.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {

    String id;
    String name;
    ProductPrice current_price;
}
