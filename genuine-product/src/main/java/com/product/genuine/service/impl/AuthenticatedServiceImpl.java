package com.product.genuine.service.impl;

import com.product.genuine.entity.Authenticated;
import com.product.genuine.entity.ProductDetail;
import com.product.genuine.entity.QAuthenticated;
import com.product.genuine.entity.QProductDetail;
import com.product.genuine.repository.AuthenticatedRepository;
import com.product.genuine.repository.ProductDetailRepository;
import com.product.genuine.service.AuthenticatedService;
import com.querydsl.core.BooleanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * AuthenticatedServiceImpl
 * Created by nuwan on 8/7/18.
 */
@Transactional
@Service
public class AuthenticatedServiceImpl implements AuthenticatedService {

    @Autowired
    private AuthenticatedRepository authenticatedRepository;

    @Autowired
    private ProductDetailRepository productDetailRepository;

    @Override
    public Boolean authenticate(String authenticateCode) {

        BooleanBuilder booleanBuilder = new BooleanBuilder(QAuthenticated.authenticated.authenticationCode.eq(authenticateCode));

        List<Authenticated> authenticates = (List<Authenticated>) authenticatedRepository.findAll(booleanBuilder);

        if(authenticates != null && authenticates.size() != 0) {
            return true;
        } else {

            Optional<ProductDetail> productDetail = authenticateProductDetail(authenticateCode);
            if(productDetail != null) {
                Authenticated authenticated = new Authenticated();
                authenticated.setAuthenticationCode(authenticateCode);
                authenticated.setProductDetail(productDetail.get());
                authenticated.setNumberOfAuthentication(new Integer(1));
                authenticatedRepository.save(authenticated);

                return true;

            }

            return false;
        }
    }

    //check auth code in product details
    public Optional<ProductDetail> authenticateProductDetail(String authenticateCode) {

        BooleanBuilder booleanBuilder = new BooleanBuilder(QProductDetail.productDetail.authenticationCode.eq(authenticateCode));

        Optional<ProductDetail> productDetails =productDetailRepository.findOne(booleanBuilder);

        /*if(productDetails != null && productDetails.size() != 0) {
            return true;
        } else {
            return false;
        }*/

        return productDetails;

    }
}
