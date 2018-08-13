package com.product.genuine.dto.response;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

/**
 * ProductViewResponse
 * Created by nuwan on 7/27/18.
 */
@Data
public class ProductViewResponse {

    private String id;

    private String name;

    private String code;

    private String description;

    private Integer quantity;

    private LocalDate expireDate;

    private Integer batchNumber;

    private String imageName;

    private List<ProductDetailData> productDetails;

    private ClientData client;

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
}
