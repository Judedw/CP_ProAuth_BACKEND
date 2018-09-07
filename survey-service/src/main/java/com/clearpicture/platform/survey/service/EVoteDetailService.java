package com.clearpicture.platform.survey.service;

import com.clearpicture.platform.survey.entity.EVoteDetail;

import java.util.List;

/**
 * EVoteDetailService
 * Created by nuwan on 9/7/18.
 */
public interface EVoteDetailService {
    List<EVoteDetail> retrieveList(Long id);
}
