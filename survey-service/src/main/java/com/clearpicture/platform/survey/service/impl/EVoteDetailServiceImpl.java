package com.clearpicture.platform.survey.service.impl;

import com.clearpicture.platform.survey.entity.EVoteDetail;
import com.clearpicture.platform.survey.repository.EVoteDetailsRepository;
import com.clearpicture.platform.survey.service.EVoteDetailService;
import com.querydsl.core.BooleanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * EVoteDetailServiceImpl
 * Created by nuwan on 9/7/18.
 */
@Service
public class EVoteDetailServiceImpl implements EVoteDetailService {

    @Autowired
    private EVoteDetailsRepository eVoteDetailsRepository;


    @Override
    public List<EVoteDetail> retrieveList(Long id) {
        BooleanBuilder builder = new BooleanBuilder();
        return (List<EVoteDetail>) eVoteDetailsRepository.findAll(builder);
    }
}
