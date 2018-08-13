package com.product.genuine.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

/**
 * FileStorageService
 * Created by nuwan on 8/8/18.
 */
public interface FileStorageService {
    String storeFile(MultipartFile file);

    Resource loadFileAsResource(String fileName);
}
