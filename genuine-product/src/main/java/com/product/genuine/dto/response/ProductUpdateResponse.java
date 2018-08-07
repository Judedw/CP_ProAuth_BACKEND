package com.product.genuine.dto.response;

import com.product.genuine.entity.Client;
import com.product.genuine.modelmapper.ModelMappingAware;
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

    private ClientData client;

    @Data
    public static class ClientData implements ModelMappingAware {


        private String id;

        @Override
        public Class<?> getDestinationType() {
            return Client.class;
        }
    }
}
