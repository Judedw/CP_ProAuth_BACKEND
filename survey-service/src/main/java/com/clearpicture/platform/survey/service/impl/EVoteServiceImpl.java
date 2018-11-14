package com.clearpicture.platform.survey.service.impl;

import com.clearpicture.platform.exception.ComplexValidationException;
import com.clearpicture.platform.survey.entity.*;
import com.clearpicture.platform.survey.entity.criteria.EVoteSearchCriteria;
import com.clearpicture.platform.survey.repository.EVoteDetailsRepository;
import com.clearpicture.platform.survey.repository.EVoteRepository;
import com.clearpicture.platform.survey.repository.EvoteImageRepository;
import com.clearpicture.platform.survey.repository.VoterRepository;
import com.clearpicture.platform.survey.service.EVoteService;
import com.querydsl.core.BooleanBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * EVoteServiceImpl
 * Created by nuwan on 8/23/18.
 */
@Transactional
@Service
public class EVoteServiceImpl implements EVoteService {

    @Autowired
    private EVoteRepository eVoteRepository;

    @Autowired
    private EvoteImageRepository imageRepository;

    @Autowired
    private VoterRepository voterRepository;

    @Autowired
    private EVoteDetailsRepository eVoteDetailsRepository;

    @Override
    public EVote save(EVote eVote) throws Exception {

        Set<EVoteDetail> eVoteDetails = new HashSet<>();
        Long lastId = eVoteDetailsRepository.getMaxId();

        if (eVote.getBatchNumber() != null) {
            //List<Voter> voters = voterRepository.findByBatchNumber(eVote.getBatchNumber());
            List<Voter> voters = voterRepository.findByBatchNumberAndClientId(eVote.getBatchNumber(), eVote.getClientId());

            if (voters.isEmpty())
                throw new ComplexValidationException("votersZero", "eVoteCreateRequest.eVoteVotersNotExists");

            for (Voter voter : voters) {
                EVoteDetail eVoteDetail = new EVoteDetail(eVote.getCode() + "/" + eVote.getClientId() + "/" + lastId++, eVote);
                eVoteDetails.add(eVoteDetail);
            }
            eVote.setEVoteDetails(eVoteDetails);
        }

        Set<EvoteImage> evImages = eVote.getImageObjects();
        if (evImages != null && !evImages.isEmpty()) {
            for (EvoteImage image : eVote.getImageObjects()) {
                image.setEvote(eVote);
            }
        }


        return eVoteRepository.save(eVote);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<EVote> search(EVoteSearchCriteria criteria) {

        PageRequest page = PageRequest.of(criteria.getPageNumber() - 1, criteria.getPageSize(),
                Sort.Direction.fromString(criteria.getSortDirection() == null ? null : criteria.getSortDirection()), criteria.getSortProperty());
        Page<EVote> eVotes = null;

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        if (StringUtils.isNotBlank(criteria.getTopic())) {
            booleanBuilder.and(QEVote.eVote.topic.equalsIgnoreCase(criteria.getTopic()));
        }

        if (booleanBuilder.hasValue()) {
            eVotes = eVoteRepository.findAll(booleanBuilder, page);
        } else {
            eVotes = eVoteRepository.findAll(page);
        }

        if (eVotes != null) {
            eVotes.forEach(s -> s.getEVoteDetails().size());
        }

        return eVotes;

    }

    @Transactional(readOnly = true)
    @Override
    public EVote retrieve(Long id) {
        try {
            EVote eVote = eVoteRepository.getOne(id);
            eVote.getEVoteDetails().size();
            return eVote;
        } catch (EntityNotFoundException e) {
            throw new ComplexValidationException("eVote", "eVoteViewRequest.eVoteNotExist");
        }

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

    @Override
    public EVote update(EVote eVote) {


        try {

            Integer batchNumber = eVote.getBatchNumber();

            EVote currentEVote = eVoteRepository.getOne(eVote.getId());
            currentEVote.setCode(eVote.getCode());
            currentEVote.setDescription(eVote.getDescription());
            currentEVote.setExpireDate(eVote.getExpireDate());
            currentEVote.setBatchNumber(batchNumber);
            currentEVote.setClientId(eVote.getClientId());
            currentEVote.setSurveyId(eVote.getSurveyId());
            currentEVote.setTopic(eVote.getTopic());

            // Currently existing image objects in database
            Set<EvoteImage> evoteImages = currentEVote.getImageObjects();

            // Image id set that is not going to be change
            Set<Long> remainImageIds = eVote.getRemainImagesID();


            // Declare and Initialize a set for newly added images
            Set<EvoteImage> toBeUpdatedSet = new HashSet<>();

            if (remainImageIds != null && !remainImageIds.isEmpty()) {
                evoteImages.forEach(image -> {
                    if (remainImageIds.contains(image.getId())) {
                        toBeUpdatedSet.add(image);
                    }
                });
            }

            // Incoming newly added Images
            Set<EvoteImage> incomingImages = eVote.getImageObjects();

            if (incomingImages != null && !incomingImages.isEmpty()) {
                // setting up the evote object to new evote image objects
                for (EvoteImage image : eVote.getImageObjects()) {
                    image.setEvote(eVote);
                }

                // add incoming new images to to be updated image list.
                toBeUpdatedSet.addAll(incomingImages);
            }

            // clear the existing set in object level
            currentEVote.getImageObjects().clear();

            // add new updated Set into current evote object
            currentEVote.getImageObjects().addAll(toBeUpdatedSet);


            // persists the content to database
            return eVoteRepository.save(currentEVote);


        } catch (EntityNotFoundException e) {
            throw new ComplexValidationException("eVote", "eVoteUpdateRequest.eVoteNotExist");
        }

    }

    @Override
    public EVote delete(Long id) {
        EVote currentSurvey = null;
        try {
            currentSurvey = eVoteRepository.getOne(id);
        } catch (EntityNotFoundException e) {
            throw new ComplexValidationException("eVote", "eVoteDeleteRequest.eVoteNotExist");
        }
        eVoteRepository.delete(currentSurvey);
        return currentSurvey;
    }

    @Override
    public EvoteImage retrieveEvoteImage(Long id) {
        try {
            return imageRepository.getOne(id);
        } catch (EntityNotFoundException e) {
            throw new ComplexValidationException("evote_image", "evoteImageRetrieve.evoteImageNotExists");
        }
    }
}
