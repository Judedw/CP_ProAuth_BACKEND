package com.clearpicture.platform.configuration;

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

    private Authenticate authenticate;

    private KeyFile keyFile;

    @Data
    public static class Crypto {

        private String password;

        private String salt;
    }

    @Data
    public static class Endpoint {

        private String api;

    }

    @Data
    public static class Authenticate {

        private String titleSuccess;

        private String titleReject;

        private String successMessage;

        private String rejectMessage;
    }

    @Data
    public static class KeyFile {

        private String pathPrivate;

        private String pathPublic;

        private String appId;
    }
}
