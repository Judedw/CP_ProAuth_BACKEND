package com.product.genuine.service.impl;

import com.product.genuine.entity.ProductDetail;
import com.product.genuine.entity.QProductDetail;
import com.product.genuine.repository.ProductDetailRepository;
import com.product.genuine.service.ProductDetailService;
import com.querydsl.core.BooleanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * ProductDetailServiceImpl
 * Created by nuwan on 8/2/18.
 */
@Transactional
@Service
public class ProductDetailServiceImpl implements ProductDetailService {

    @Autowired
    private ProductDetailRepository productDetailRepository;


    @Transactional(readOnly = true)
    @Override
    public List<ProductDetail> retrieveList(Long productId) {

        BooleanBuilder booleanBuilder = new BooleanBuilder(QProductDetail.productDetail.product.id.eq(productId));

        /*if (criteria.getClientId() != null) {
            booleanBuilder.and(QProductDetail.productDetail.product.client.id.eq(criteria.getClientId()));
        }

        if(criteria.getBatchNumber() != null) {
            booleanBuilder.and(QProductDetail.productDetail.product.batchNumber.eq(criteria.getBatchNumber()));
        }

        if(StringUtils.isNotBlank(criteria.getCode())) {
            booleanBuilder.and(QProductDetail.productDetail.product.code.containsIgnoreCase(criteria.getCode()));
        }*/

        return (List<ProductDetail>) productDetailRepository.findAll(booleanBuilder);

    }

    @Override
    public Boolean authenticate(String authenticateCode) {

        BooleanBuilder booleanBuilder = new BooleanBuilder(QProductDetail.productDetail.authenticationCode.eq(authenticateCode));

        List<ProductDetail> productDetails = (List<ProductDetail>) productDetailRepository.findAll(booleanBuilder);

        if(productDetails != null && productDetails.size() != 0) {
            return true;
        } else {
            return false;
        }

    }
}
