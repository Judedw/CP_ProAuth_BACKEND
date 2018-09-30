package com.clearpicture.platform.survey.controller;

import com.clearpicture.platform.configuration.PlatformConfigProperties;
import com.clearpicture.platform.enums.SurveyType;
import com.clearpicture.platform.modelmapper.ModelMapper;
import com.clearpicture.platform.response.wrapper.ListResponseWrapper;
import com.clearpicture.platform.service.CryptoService;
import com.clearpicture.platform.survey.dto.response.EVoteDetailResponse;
import com.clearpicture.platform.survey.entity.EVoteDetail;
import com.clearpicture.platform.survey.service.EVoteDetailService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.encrypt.BytesEncryptor;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

/**
 * EVoteDetailController
 * Created by nuwan on 9/7/18.
 */
@Slf4j
@RestController
public class EVoteDetailController {

    @Autowired
    private EVoteDetailService eVoteDetailService;

    @Autowired
    private CryptoService cryptoService;

    @Autowired
    private ModelMapper modelMapper;

    private BytesEncryptor bytesEncryptor;

    private static final String UNICODE_FORMAT = "ISO-8859-1";

    @Autowired
    private PlatformConfigProperties configs;

    @PostConstruct
    public void init() {
        bytesEncryptor = Encryptors.stronger(configs.getCrypto().getPassword(), configs.getCrypto().getSalt());
    }
    @GetMapping("${app.endpoint.eVoteDetails}")
    public ResponseEntity<ListResponseWrapper<EVoteDetailResponse>> retrieveList(@PathVariable String eVoteId) throws GeneralSecurityException, IOException {

        //ProductDetailSearchCriteria criteria = modelMapper.map(request,ProductDetailSearchCriteria.class);
        Long id = cryptoService.decryptEntityId(eVoteId);

        List<EVoteDetail> results = eVoteDetailService.retrieveList(id);

        List<EVoteDetailResponse> eVoteDetails =  modelMapper.map(results,EVoteDetailResponse.class);

        List<EVoteDetailResponse> detailResponses = new ArrayList<>();

        eVoteDetails.forEach(productDetail -> {
            EVoteDetailResponse response = new EVoteDetailResponse();
            try {
                String uniqueAuthCode = productDetail.getAuthenticationCode()+"/"+ SurveyType.EVOTE.getValue();
                byte[] encVal = bytesEncryptor.encrypt(uniqueAuthCode.getBytes(UNICODE_FORMAT));
                response.setAuthenticationCode(Hex.encodeHexString(encVal));
                /*byte[] encVal = bytesEncryptor.encrypt(productDetail.getAuthenticationCode().getBytes(UNICODE_FORMAT));
                response.setAuthenticationCode(Hex.encodeHexString(encVal));*/
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            detailResponses.add(response);

        });
        log.info("detailResponses ------->"+detailResponses.size());

        return new ResponseEntity<ListResponseWrapper<EVoteDetailResponse>>(new ListResponseWrapper<EVoteDetailResponse>(detailResponses), HttpStatus.OK);
    }
}
