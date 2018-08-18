package com.clearpicture.platform.product.entity;

import lombok.Data;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * ProductIdentity
 * Created by nuwan on 7/27/18.
 */
@Data
@Embeddable
public class ProductIdentity implements Serializable {

    @NotNull
    @Size(max = 20)
    private String productCode;

    @NotNull
    @Size(max = 20)
    private String clientId;

    public ProductIdentity(){}

    public ProductIdentity(String productCode,String clientId){
        this.productCode = productCode;
        this.clientId = clientId;
    }
}
