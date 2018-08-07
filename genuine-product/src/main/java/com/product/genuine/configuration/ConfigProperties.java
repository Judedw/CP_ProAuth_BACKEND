package com.product.genuine.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * ConfigProperties
 * Created by nuwan on 7/21/18.
 */
@Data
@ConfigurationProperties("app")
@Configuration
public class ConfigProperties {

    private Crypto crypto;

    //private Auth auth;

    @Data
    public static class Crypto {

        private String password;

        private String salt;
    }

    /*@Data
    public static class Auth {

        private String resourceId;

        private String ksPass;

        private String kfName;
    }*/
}
