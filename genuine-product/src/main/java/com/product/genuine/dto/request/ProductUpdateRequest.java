package com.product.genuine.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

/**
 * ProductUpdateRequest
 * Created by nuwan on 7/27/18.
 */
@Data
public class ProductUpdateRequest {

    private String code;

    private String description;

    private Integer quantity;

    private LocalDate expireDate;

    private Integer batchNumber;

    private ClientData client;

    private String name;

    @Data
    public static class ClientData {

        @NotBlank(message = "platformClientUpdateRequest.product.id.empty")
        private String id;

    }
}
