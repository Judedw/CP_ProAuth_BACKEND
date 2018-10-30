package com.clearpicture.platform.product.dto.response;

import com.clearpicture.platform.product.dto.request.ProductUpdateRequest;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

/**
 * ProductUpdateResponse
 * Created by nuwan on 7/27/18.
 */
@Data
public class ProductUpdateResponse {

    private String id;

    private String code;

    private String description;

    private Integer quantity;

    private LocalDate expireDate;

    private Integer batchNumber;

    private String name;

    private ProductUpdateResponse.ClientData client;

    private Set<ProductImageData> imageObjects;

    private String surveyId;


    @Data
    public static class ClientData  {

        private String id;

    }

    @Data
    public static class ProductImageData {
        private String id;
    }
}
