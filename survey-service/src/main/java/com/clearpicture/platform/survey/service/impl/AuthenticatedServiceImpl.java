package com.clearpicture.platform.survey.service.impl;

import com.clearpicture.platform.survey.entity.Authenticated;
import com.clearpicture.platform.survey.entity.EVoteDetail;
import com.clearpicture.platform.survey.entity.QAuthenticated;
import com.clearpicture.platform.survey.entity.QEVoteDetail;
import com.clearpicture.platform.survey.repository.AuthenticatedRepository;
import com.clearpicture.platform.survey.repository.EVoteDetailsRepository;
import com.clearpicture.platform.survey.service.AuthenticatedService;
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
    private EVoteDetailsRepository eVoteDetailsRepository;

    @Override
    public Authenticated authenticate(String authenticateCode) {

        log.info("authenticateCode : {}",authenticateCode);

        BooleanBuilder booleanBuilder = new BooleanBuilder(QAuthenticated.authenticated.authenticationCode.eq(authenticateCode));

        Optional<Authenticated> authenticate =authenticatedRepository.findOne(booleanBuilder);


        log.info("authenticates : {}",authenticate);


        if(authenticate != null && authenticate.isPresent()) {
            log.info("authenticates get: {}",authenticate.get());
            Authenticated authenticated = authenticate.get();
            authenticated.setNumberOfAuthentication(authenticated.getNumberOfAuthentication()+1);
            authenticatedRepository.save(authenticated);
            return authenticated;
        } else {

            Optional<EVoteDetail> eVoteDetail = authenticateEVoteDetail(authenticateCode);
            if(eVoteDetail != null) {
                Authenticated authenticated = new Authenticated();
                authenticated.setAuthenticationCode(authenticateCode);
                authenticated.setEVoteDetail(eVoteDetail.get());
                authenticated.setNumberOfAuthentication(new Integer(1));
                authenticatedRepository.save(authenticated);

                return authenticated;

            }

            return null;
        }
    }

    //check auth code in product details
    public Optional<EVoteDetail> authenticateEVoteDetail(String authenticateCode) {

        BooleanBuilder booleanBuilder = new BooleanBuilder(QEVoteDetail.eVoteDetail.authenticationCode.eq(authenticateCode));

        Optional<EVoteDetail> eVoteDetail =eVoteDetailsRepository.findOne(booleanBuilder);

        /*if(productDetails != null && productDetails.size() != 0) {
            return true;
        } else {
            return false;
        }*/

        return eVoteDetail;

    }
}
