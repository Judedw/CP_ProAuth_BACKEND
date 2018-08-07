package com.product.genuine.dto.response;

import lombok.Data;

import java.time.LocalDate;

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

    private ClientData client;


    @Data
    public static class ClientData  {

        private String id;

    }
}
