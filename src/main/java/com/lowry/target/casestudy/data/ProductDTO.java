package com.lowry.target.casestudy.data;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@lombok.Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

    public String tcin;
    public ItemDTO item;
}
