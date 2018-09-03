package com.clearpicture.platform.survey.service.impl;

import com.clearpicture.platform.survey.entity.EVote;
import com.clearpicture.platform.survey.entity.QEVote;
import com.clearpicture.platform.survey.entity.criteria.EVoteSearchCriteria;
import com.clearpicture.platform.survey.repository.EVoteRepository;
import com.clearpicture.platform.survey.service.EVoteService;
import com.querydsl.core.BooleanBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * EVoteServiceImpl
 * Created by nuwan on 8/23/18.
 */
@Transactional
@Service
public class EVoteServiceImpl implements EVoteService {

    @Autowired
    private EVoteRepository eVoteRepository;

    @Override
    public EVote save(EVote eVote) {
        return eVoteRepository.save(eVote);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<EVote> search(EVoteSearchCriteria criteria) {

        PageRequest page = PageRequest.of(criteria.getPageNumber() - 1, criteria.getPageSize(),
                Sort.Direction.fromString(criteria.getSortDirection() == null ? null : criteria.getSortDirection()), criteria.getSortProperty());
        Page<EVote> eVotes = null;

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        if(StringUtils.isNotBlank(criteria.getTopic())) {
            booleanBuilder.and(QEVote.eVote.topic.equalsIgnoreCase(criteria.getTopic()));
        }

        if (booleanBuilder.hasValue()) {
            eVotes = eVoteRepository.findAll(booleanBuilder, page);
        } else {
            eVotes = eVoteRepository.findAll(page);
        }

        return eVotes;

    }

    @Transactional(readOnly = true)
    @Override
    public EVote retrieve(Long id) {
        EVote eVote = eVoteRepository.getOne(id);
        /*if(product != null) {
            product.getProductDetails().size();
        }*/
        return eVote;
    }

    @Transactional(readOnly = true)
    @Override
    public List<EVote> retrieveForSuggestions(String keyword) {

        if (StringUtils.isNotBlank(keyword)) {
            BooleanBuilder booleanBuilder = new BooleanBuilder(
                    QEVote.eVote.topic.containsIgnoreCase(keyword));
            return (List<EVote>) eVoteRepository.findAll(booleanBuilder);
        } else {
            BooleanBuilder booleanBuilder = new BooleanBuilder();
            return (List<EVote>) eVoteRepository.findAll(booleanBuilder);
        }

    }
}
