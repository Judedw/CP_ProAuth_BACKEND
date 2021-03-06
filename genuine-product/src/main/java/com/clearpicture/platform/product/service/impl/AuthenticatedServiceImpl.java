package com.clearpicture.platform.product.service.impl;

import com.clearpicture.platform.configuration.PlatformConfigProperties;
import com.clearpicture.platform.enums.SurveyType;
import com.clearpicture.platform.product.entity.Authenticated;
import com.clearpicture.platform.product.entity.Product;
import com.clearpicture.platform.product.entity.ProductDetail;
import com.clearpicture.platform.product.entity.QAuthenticated;
import com.clearpicture.platform.product.entity.QProductDetail;
import com.clearpicture.platform.product.repository.AuthenticatedRepository;
import com.clearpicture.platform.product.repository.ProductDetailRepository;
import com.clearpicture.platform.product.service.AuthenticatedService;
import com.clearpicture.platform.util.AuthenticatedConstant;
import com.querydsl.core.BooleanBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.encrypt.BytesEncryptor;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.security.Security;
import java.util.HashMap;
import java.util.Map;
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

    private BytesEncryptor bytesEncryptor;

    @Autowired
    private PlatformConfigProperties configs;

    @PostConstruct
    public void init() {
        Security.setProperty("crypto.policy", "unlimited");
        bytesEncryptor = Encryptors.stronger(configs.getCrypto().getPassword(), configs.getCrypto().getSalt());
    }

    @Override
    public Map<String,Object>  authenticate(String authenticateCode) throws Exception {

        log.info("authenticateCode : {}",authenticateCode);

        String authCode = getAuthenticateCode(authenticateCode);

        Map<String,Object> authenticatedMap = new HashMap<>();

        Optional<ProductDetail> productDetail = authenticateProductDetail(authCode);

        if(productDetail != null) {
            authenticatedMap.put(AuthenticatedConstant.AUTH_STATUS,Boolean.TRUE);
            ProductDetail dbProductDetail = productDetail.get();
            Product product = dbProductDetail.getProduct();

            log.info("product : {}",product);

            Long surveyId = product.getSurveyId();

            log.info("surveyId : {}",surveyId);

            BooleanBuilder booleanBuilder = new BooleanBuilder(QAuthenticated.authenticated.authenticationCode.eq(authenticateCode));

            Optional<Authenticated> authenticate =authenticatedRepository.findOne(booleanBuilder);

            log.info("authenticates : {}",authenticate);
            if(authenticate != null && authenticate.isPresent()) {
                log.info("authenticates get: {}",authenticate.get());
                Authenticated authenticated = authenticate.get();
                authenticated.setNumberOfAuthentication(authenticated.getNumberOfAuthentication()+1);
                authenticatedRepository.save(authenticated);

            } else {
                Authenticated authenticated = new Authenticated();
                authenticated.setAuthenticationCode(authenticateCode);
                authenticated.setProductDetail(productDetail.get());
                authenticated.setNumberOfAuthentication(new Integer(1));
                authenticatedRepository.save(authenticated);
            }

            if(surveyId != null) {
                authenticatedMap.put(AuthenticatedConstant.SURVEY_ID,surveyId);
            } else {
                authenticatedMap.put(AuthenticatedConstant.SURVEY_ID,0L);
            }


        } else {
            authenticatedMap.put("status",Boolean.FALSE);

        }

        log.info("authenticatedMap : {}",authenticatedMap);

        return authenticatedMap;
    }

    //check auth code in product details
    public Optional<ProductDetail> authenticateProductDetail(String authenticateCode) {

        BooleanBuilder booleanBuilder = new BooleanBuilder(QProductDetail.productDetail.authenticationCode.eq(authenticateCode));

        Optional<ProductDetail> productDetails =productDetailRepository.findOne(booleanBuilder);

        return productDetails;

    }

    /*Divided into product and evote*/
    public String getAuthenticateCode(String authenticateCode) throws DecoderException {
        String authenticationCode = new String(bytesEncryptor.decrypt(Hex.decodeHex(authenticateCode)));
        String[] authCodeType = authenticationCode.split("/");
        if(authCodeType[1].equals(SurveyType.PRODUCT.getValue())) {
            log.info("authenticationCode type -->"+authCodeType[1]);
            return authCodeType[0];
        } else if(authCodeType[1].equals(SurveyType.EVOTE.getValue())) {
            log.info("authenticationCode type -->"+authCodeType[1]);
        }
        return null;
    }
}
