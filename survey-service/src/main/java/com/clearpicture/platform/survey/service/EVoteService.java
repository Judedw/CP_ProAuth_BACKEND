package com.clearpicture.platform.survey.service;

import com.clearpicture.platform.survey.entity.EVote;
import com.clearpicture.platform.survey.entity.criteria.EVoteSearchCriteria;
import org.springframework.data.domain.Page;

/**
 * EVoteService
 * Created by nuwan on 8/23/18.
 */
public interface EVoteService {
    EVote save(EVote eVote);

    Page<EVote> search(EVoteSearchCriteria criteria);

    EVote retrieve(Long productId);
}
