package com.clearpicture.platform.product.configuration;

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

    private ExternalEndpointProduct externalEndpointProduct;

    private ExternalEndpointEVote externalEndpointEVote;

    @Data
    public static class Crypto {

        private String password;

        private String salt;
    }

    @Data
    public static class ExternalEndpointProduct {

        private String authProductDetail;

    }

    @Data
    public static class ExternalEndpointEVote {

        private String authEVoteDetail;

        private String validateSurvey;

    }
}
