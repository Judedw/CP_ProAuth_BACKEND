package com.product.genuine.service;

import com.product.genuine.entity.ProductDetail;

import java.util.List;

/**
 * ProductDetailService
 * Created by nuwan on 8/2/18.
 */
public interface ProductDetailService {

    List<ProductDetail> retrieveList(Long productId);

    Boolean authenticate(String authenticateCode);
}
