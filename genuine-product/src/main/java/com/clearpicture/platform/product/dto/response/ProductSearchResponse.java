package com.clearpicture.platform.product.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

/**
 * ProductSearchResponse
 * Created by nuwan on 7/26/18.
 */
@Data
public class ProductSearchResponse {

    private String id;

    private String name;

    private String code;

    private String description;

    private Integer quantity;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate expireDate;

    private Integer batchNumber;

    //private String imageName;
    //private byte[] imageObject;

    private Set<ProductImageData> imageObjects;

    private List<ProductDetailData> productDetails;

    private ClientData client;

    private String surveyId;


    @Data
    public static class ProductDetailData {

        private String uniqueProductCode;

        private String authenticationCode;

    }

    @Data
    public static class ClientData {

        private String id;

        private String name;
    }

    @Data
    public static class ProductImageData {
        private String id;
    }
}
