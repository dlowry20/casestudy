package com.lowry.target.casestudy.data;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@lombok.Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemDTO {

    public ProductDescriptionDTO product_description;
    public EnrichmentDTO enrichment;
    public ProductClassificationDTO product_classification;
    public PrimaryBrandDTO primary_brand;

}
