package com.clearpicture.platform.product.controller;


import com.clearpicture.platform.configuration.PlatformConfigProperties;
import com.clearpicture.platform.product.service.AuthenticatedService;
import com.clearpicture.platform.product.service.ProductDetailService;
import com.clearpicture.platform.service.CryptoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.encrypt.BytesEncryptor;
import org.springframework.web.bind.annotation.RestController;

/**
 * AutheniticatedController
 * Created by nuwan on 8/7/18.
 */
@Slf4j
@RestController
public class AuthenticatedController {

    @Autowired
    private ProductDetailService productDetailService;

    @Autowired
    private AuthenticatedService authenticatedService;

    private BytesEncryptor bytesEncryptor;

    @Autowired
    private PlatformConfigProperties configs;

    @Autowired
    private CryptoService cryptoService;

    /*@PostConstruct
    public void init() {
        Security.setProperty("crypto.policy", "unlimited");
        bytesEncryptor = Encryptors.stronger(configs.getCrypto().getPassword(), configs.getCrypto().getSalt());
    }*/

    /*@GetMapping("${app.endpoint.productAuthenticate}")
    public ResponseEntity<SimpleResponseWrapper<ProductAuthenticateResponse>> authenticate(@RequestParam String authCode) {
        ProductAuthenticateResponse productAuthenticateResponse = new ProductAuthenticateResponse();
        Long surveyId =0L;
        Boolean checkAllReadyAuthenticated = Boolean.FALSE;
        Map<String,Object> authenticatedMap = new HashMap<>();
        try {
            authenticatedMap = authenticatedService.authenticate((authCode));
            checkAllReadyAuthenticated = (Boolean) authenticatedMap.get(AuthenticatedConstant.AUTH_STATUS);
            surveyId = (Long) authenticatedMap.get(AuthenticatedConstant.SURVEY_ID);
            log.info("surveyId {}",surveyId);
        } catch (Exception e) {
            e.printStackTrace();
            productAuthenticateResponse.setTitle(configs.getAuthenticate().getTitleReject());
            productAuthenticateResponse.setMessage(configs.getAuthenticate().getRejectMessage());
        }

        log.info("checkAllReadyAuthenticated {}",checkAllReadyAuthenticated);

        if(checkAllReadyAuthenticated) {
            productAuthenticateResponse.setTitle(configs.getAuthenticate().getTitleSuccess());
            productAuthenticateResponse.setMessage(configs.getAuthenticate().getSuccessMessage());
            productAuthenticateResponse.setSurveyId(cryptoService.encryptEntityId(surveyId));
        } else {
            productAuthenticateResponse.setTitle(configs.getAuthenticate().getTitleReject());
            productAuthenticateResponse.setMessage(configs.getAuthenticate().getRejectMessage());
        }
        return new ResponseEntity<SimpleResponseWrapper<ProductAuthenticateResponse>>(new SimpleResponseWrapper<ProductAuthenticateResponse>(productAuthenticateResponse), HttpStatus.OK);
    }*/
}
