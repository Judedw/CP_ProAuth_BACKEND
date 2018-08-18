package com.clearpicture.platform.product.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * FileStorageProperties
 * Created by nuwan on 8/7/18.
 */
@ConfigurationProperties(prefix = "file")
@Data
@Configuration
public class FileStorageProperties {

    private String uploadDir;


}
