package com.lowry.target.casestudy.data;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@lombok.Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDTO {

    public DataDTO data;

    public String getProductTitle() {
        return this.getData().getProduct().getItem().getProduct_description().getTitle();
    }

    public String getProductId() {
        return this.getData().getProduct().getTcin();
    }
}
