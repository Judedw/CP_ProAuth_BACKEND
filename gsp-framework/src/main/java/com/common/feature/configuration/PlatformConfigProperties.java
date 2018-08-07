package com.common.feature.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * PlatformConfigProperties
 * Created by nuwan on 7/21/18.
 */
@Data
@ConfigurationProperties("app")
@Configuration
public class PlatformConfigProperties {

    private Crypto crypto;

    private Endpoint endpoint;

    @Data
    public static class Crypto {

        private String password;

        private String salt;
    }

    @Data
    public static class Endpoint {

        private String api;

    }
}
