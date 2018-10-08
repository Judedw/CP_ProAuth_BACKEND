package com.clearpicture.platform.product.service.impl;

import com.clearpicture.platform.configuration.PlatformConfigProperties;
import com.clearpicture.platform.enums.SurveyType;
import com.clearpicture.platform.exception.ComplexValidationException;
import com.clearpicture.platform.product.dto.response.ProductAuthenticateResponse;
import com.clearpicture.platform.product.entity.Authenticated;
import com.clearpicture.platform.product.entity.Product;
import com.clearpicture.platform.product.entity.ProductDetail;
import com.clearpicture.platform.product.entity.QAuthenticated;
import com.clearpicture.platform.product.entity.QProductDetail;
import com.clearpicture.platform.product.repository.AuthenticatedRepository;
import com.clearpicture.platform.product.repository.ProductDetailRepository;
import com.clearpicture.platform.product.service.AuthenticationService;
import com.clearpicture.platform.product.service.Ms2msCommunicationService;
import com.clearpicture.platform.service.CryptoService;
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
 * AuthenticationServiceImpl
 * Created by nuwan on 9/23/18.
 */
@Slf4j
@Transactional
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private BytesEncryptor bytesEncryptor;

    @Autowired
    private PlatformConfigProperties configs;

    @Autowired
    private Ms2msCommunicationService ms2msCommunicationService;

    @Autowired
    private ProductDetailRepository productDetailRepository;

    @Autowired
    private AuthenticatedRepository authenticatedRepository;

    @Autowired
    private CryptoService cryptoService;


    @PostConstruct
    public void init() {
        Security.setProperty("crypto.policy", "unlimited");
        bytesEncryptor = Encryptors.stronger(configs.getCrypto().getPassword(), configs.getCrypto().getSalt());
    }

    @Override
    public ProductAuthenticateResponse authenticate(String authenticateCode) {
        Long surveyId =0L;
        Boolean checkAllReadyAuthenticated = Boolean.FALSE;
        ProductAuthenticateResponse productAuthenticateResponse = new ProductAuthenticateResponse();
        ProductAuthenticateResponse.ProductAuthData authData = new ProductAuthenticateResponse.ProductAuthData();

        try {
            String authenticationCode = new String(bytesEncryptor.decrypt(Hex.decodeHex(authenticateCode)));
            String[] authCodeType = authenticationCode.split("/");
            if(authCodeType[1].equals(SurveyType.PRODUCT.getValue())) {
                log.info("authenticationCode type -->"+authCodeType[1]);

                Map<String,Object> authenticatedMap = new HashMap<>();

                authenticatedMap = productAuthenticate(authCodeType[0]);

                if((Boolean) authenticatedMap.get(AuthenticatedConstant.AUTH_STATUS)) {
                    authData.setTitle(configs.getAuthenticate().getTitleSuccess());
                    authData.setMessage(configs.getAuthenticate().getSuccessMessage());
                    if(authenticatedMap.get(AuthenticatedConstant.SURVEY_ID) != null) {
                        authData.setSurveyId(cryptoService.encryptEntityId((Long) authenticatedMap.get(AuthenticatedConstant.SURVEY_ID)));
                    }

                } else {
                    authData.setTitle(configs.getAuthenticate().getTitleReject());
                    authData.setMessage(configs.getAuthenticate().getRejectMessage());
                }

            } else if(authCodeType[1].equals(SurveyType.EVOTE.getValue())) {
                log.info("authenticationCode type -->"+authCodeType[1]);
                productAuthenticateResponse = ms2msCommunicationService.authenticateEVote(authCodeType[0]);
                if(productAuthenticateResponse!= null) {
                    authData.setTitle(productAuthenticateResponse.getContent().getTitle());
                    authData.setMessage(productAuthenticateResponse.getContent().getMessage());
                    authData.setSurveyId(productAuthenticateResponse.getContent().getSurveyId());
                } else {
                    authData.setTitle(configs.getAuthenticate().getTitleReject());
                    authData.setMessage(configs.getAuthenticate().getRejectMessage());
                }

            }
        } catch (DecoderException e) {
            throw new ComplexValidationException("authenticationCode", "Invalid Authentication Code");
        } catch (Exception e) {
            throw new ComplexValidationException("authenticationCode", "General Error");
        }

        productAuthenticateResponse.setContent(authData);
        return productAuthenticateResponse;
    }

    public Map<String,Object> productAuthenticate(String authenticateCode) throws Exception {

        log.info("authenticateCode : {}",authenticateCode);

        Map<String,Object> authenticatedMap = new HashMap<>();

        Optional<ProductDetail> productDetail = authenticateProductDetail(authenticateCode);

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


}
