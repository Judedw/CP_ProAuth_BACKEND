package com.clearpicture.platform.product.entity.criteria;

import com.clearpicture.platform.product.entity.Client;
import lombok.Data;

/**
 * ClientSearchCriteria
 * Created by nuwan on 7/21/18.
 */
@Data
public class ClientSearchCriteria extends Client {

    private String name;

    private Integer pageNumber;

    private Integer pageSize;

    private String sortProperty;

    private String sortDirection;
}
