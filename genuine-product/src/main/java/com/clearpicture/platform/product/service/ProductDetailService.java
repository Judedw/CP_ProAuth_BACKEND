package com.clearpicture.platform.product.service;

import com.clearpicture.platform.product.entity.ProductDetail;

import java.util.List;

/**
 * ProductDetailService
 * Created by nuwan on 8/2/18.
 */
public interface ProductDetailService {

    List<ProductDetail> retrieveList(Long productId);

    Boolean authenticate(String authenticateCode);
}
