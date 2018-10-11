package com.clearpicture.platform.survey.service;

import com.clearpicture.platform.survey.entity.EVote;
import com.clearpicture.platform.survey.entity.criteria.EVoteSearchCriteria;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * EVoteService
 * Created by nuwan on 8/23/18.
 */
public interface EVoteService {
    EVote save(EVote eVote) throws Exception;

    Page<EVote> search(EVoteSearchCriteria criteria);

    EVote retrieve(Long eVoteId);

    List<EVote> retrieveForSuggestions(String keyword);

    EVote update(EVote eVote);

    EVote delete(Long id);
}
