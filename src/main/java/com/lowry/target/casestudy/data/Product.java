package com.lowry.target.casestudy.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@lombok.Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    public String tcin;
    public Item item;
}
