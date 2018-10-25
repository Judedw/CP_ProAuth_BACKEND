package com.clearpicture.platform.product.dto.response;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

/**
 * ProductCreateResponse
 * Created by nuwan on 7/26/18.
 */
@Data
public class ProductCreateResponse {

    private String id;

    private String code;

    private String description;

    private Integer quantity;

    private LocalDate expireDate;

    private Integer batchNumber;

    private String name;

    private ClientData client;

    //private String imageName;
    //private String imageObject;
    //private byte[] imageObject;
    private Set<ProductImageData> imageObjects;

    private String surveyId;


    @Data
    public static class ClientData {
        private String id;
    }

    @Data
    public static class ProductImageData {
        private String id;
    }


}
