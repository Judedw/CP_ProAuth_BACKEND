package com.product.genuine.dto.request;

import com.product.genuine.entity.Client;
import com.product.genuine.modelmapper.ModelMappingAware;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

/**
 * ProductRequest
 * Created by nuwan on 7/12/18.
 * (C) Copyright 2018 nuwan (http://www.efuturesworld.com/) and others.
 */
@Data
public class ProductCreateRequest {

    private String code;

    private String description;

    private Integer quantity;

    private LocalDate expireDate;

    private Integer batchNumber;

    private ClientData client;

    private String name;


    @Data
    public static class ClientData implements ModelMappingAware {

        @NotBlank(message = "platformProductCreateRequest.product.id.empty")
        private String id;

        @Override
        public Class<?> getDestinationType() {
            return Client.class;
        }
    }
}
