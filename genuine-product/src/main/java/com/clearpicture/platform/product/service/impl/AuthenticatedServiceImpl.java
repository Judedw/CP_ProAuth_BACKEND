package com.clearpicture.platform.product.service.impl;

import com.clearpicture.platform.product.entity.ProductDetail;
import com.clearpicture.platform.product.service.AuthenticatedService;
import com.clearpicture.platform.product.entity.Authenticated;
import com.clearpicture.platform.product.entity.QAuthenticated;
import com.clearpicture.platform.product.entity.QProductDetail;
import com.clearpicture.platform.product.repository.AuthenticatedRepository;
import com.clearpicture.platform.product.repository.ProductDetailRepository;
import com.querydsl.core.BooleanBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * AuthenticatedServiceImpl
 * Created by nuwan on 8/7/18.
 */
@Slf4j
@Transactional
@Service
public class AuthenticatedServiceImpl implements AuthenticatedService {

    @Autowired
    private AuthenticatedRepository authenticatedRepository;

    @Autowired
    private ProductDetailRepository productDetailRepository;

    @Override
    public Boolean authenticate(String authenticateCode) {

        log.info("authenticateCode : {}",authenticateCode);

        BooleanBuilder booleanBuilder = new BooleanBuilder(QAuthenticated.authenticated.authenticationCode.eq(authenticateCode));

        Optional<Authenticated> authenticate =authenticatedRepository.findOne(booleanBuilder);


        log.info("authenticates : {}",authenticate);


        if(authenticate != null && authenticate.isPresent()) {
            log.info("authenticates get: {}",authenticate.get());
            Authenticated authenticated = authenticate.get();
            authenticated.setNumberOfAuthentication(authenticated.getNumberOfAuthentication()+1);
            authenticatedRepository.save(authenticated);
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
