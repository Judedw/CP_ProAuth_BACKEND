package com.clearpicture.platform.product.entity.criteria;

import com.clearpicture.platform.product.entity.Product;
import lombok.Data;

/**
 * ProductSearchCriteria
 * Created by nuwan on 7/26/18.
 */
@Data
public class ProductSearchCriteria extends Product {

    private String name;

    private Integer pageNumber;

    private Integer pageSize;

    private String sortProperty;

    private String sortDirection;
}
