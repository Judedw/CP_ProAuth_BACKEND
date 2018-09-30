package com.clearpicture.platform.survey.service.impl;

import com.clearpicture.platform.survey.entity.Authenticated;
import com.clearpicture.platform.survey.entity.EVote;
import com.clearpicture.platform.survey.entity.EVoteDetail;
import com.clearpicture.platform.survey.entity.QAuthenticated;
import com.clearpicture.platform.survey.entity.QEVoteDetail;
import com.clearpicture.platform.survey.entity.Survey;
import com.clearpicture.platform.survey.repository.AuthenticatedRepository;
import com.clearpicture.platform.survey.repository.EVoteDetailsRepository;
import com.clearpicture.platform.survey.repository.SurveyRepository;
import com.clearpicture.platform.survey.service.AuthenticatedService;
import com.clearpicture.platform.util.AuthenticatedConstant;
import com.querydsl.core.BooleanBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private EVoteDetailsRepository eVoteDetailsRepository;

    @Autowired
    private SurveyRepository surveyRepository;

    @Override
    public Map<String,Object> authenticate(String authenticateCode) throws Exception {

        log.info("authenticateCode : {}",authenticateCode);

        Map<String,Object> authenticatedMap = new HashMap<>();
        Long surveyId=0L;

        Optional<EVoteDetail> eVoteDetail = authenticateEVoteDetail(authenticateCode);

        if(eVoteDetail != null) {
            authenticatedMap.put(AuthenticatedConstant.AUTH_STATUS,Boolean.TRUE);
            EVoteDetail dbEVoteDetail = eVoteDetail.get();
            EVote eVote = dbEVoteDetail.getEVote();
            log.info("eVote : {}",eVote);
            if(eVote != null) {
                Survey survey = surveyRepository.findByVoteId(eVote.getId());
                if(survey !=null)
                    surveyId = survey.getId();
                log.info("surveyId : {}",surveyId);
            }

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
                authenticated.setEVoteDetail(eVoteDetail.get());
                authenticated.setNumberOfAuthentication(new Integer(1));
                authenticatedRepository.save(authenticated);

            }if(surveyId != null) {
                log.info("set server id to map --> {}",surveyId);
                authenticatedMap.put(AuthenticatedConstant.SURVEY_ID,surveyId);
            } /*else {
                authenticatedMap.put(AuthenticatedConstant.SURVEY_ID,0L);
            }*/
        } else {
            authenticatedMap.put("status",Boolean.FALSE);
        }

        log.info("authenticatedMap : {}",authenticatedMap);
        return authenticatedMap;

    }

    //check auth code in product details
    public Optional<EVoteDetail> authenticateEVoteDetail(String authenticateCode) {

        BooleanBuilder booleanBuilder = new BooleanBuilder(QEVoteDetail.eVoteDetail.authenticationCode.eq(authenticateCode));

        Optional<EVoteDetail> eVoteDetail =eVoteDetailsRepository.findOne(booleanBuilder);

        return eVoteDetail;

    }
}
