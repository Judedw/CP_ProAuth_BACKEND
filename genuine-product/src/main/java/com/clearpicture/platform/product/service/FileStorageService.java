package com.clearpicture.platform.product.service;

import org.apache.chemistry.opencmis.client.api.Session;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * FileStorageService
 * Created by nuwan on 8/8/18.
 */
public interface FileStorageService {
    String storeFile(MultipartFile file) throws IOException, ServletException;

    //Resource loadFileAsResource(String fileName);

    Session connectToEcm() throws IOException, ServletException;
}
