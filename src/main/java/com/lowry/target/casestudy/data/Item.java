package com.lowry.target.casestudy.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@lombok.Data
@AllArgsConstructor
@NoArgsConstructor
public class Item {

    public ProductDescription product_description;
    public Enrichment enrichment;
    public ProductClassification product_classification;
    public PrimaryBrand primary_brand;

}
