package com.clearpicture.platform.product.entity.criteria;

import lombok.Data;

/**
 * ProductDetailSearchCriteria
 * Created by nuwan on 8/2/18.
 */
@Data
public class ProductDetailSearchCriteria {

    private Long clientId;

    private Integer batchNumber;

    private String code;
}
