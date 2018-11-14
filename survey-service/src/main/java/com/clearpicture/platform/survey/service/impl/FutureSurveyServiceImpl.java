package com.clearpicture.platform.survey.service.impl;

import com.clearpicture.platform.exception.ComplexValidationException;
import com.clearpicture.platform.survey.entity.Element;
import com.clearpicture.platform.survey.entity.FutureSurvey;
import com.clearpicture.platform.survey.entity.Page;
import com.clearpicture.platform.survey.entity.QFutureSurvey;
import com.clearpicture.platform.survey.entity.criteria.FutureSurveySearchCriteria;
import com.clearpicture.platform.survey.repository.FutureSurveyRepository;
import com.clearpicture.platform.survey.service.FutureSurveyService;
import com.querydsl.core.BooleanBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Set;


/**
 * created by Raveen -  18/10/24
 * FutureSurveyController - Handling every advanced surveys & e votes
 */

@Transactional
@Service
public class FutureSurveyServiceImpl implements FutureSurveyService {


    @Autowired
    private FutureSurveyRepository futureSurveyRepository;

    @Override
    public FutureSurvey save(FutureSurvey futureSurvey) {

        System.out.println("TITLE : " + futureSurvey.getTitle());
        System.out.println("client id  : " + futureSurvey.getClientId());
        System.out.println("JSON content : " + futureSurvey.getJsonContent());

        for (Page page : futureSurvey.getPages()) {
            //System.out.println("PAGE NAME : " + page.getName());
            page.setFutureSurvey(futureSurvey);

            Set<Element> elementSet = page.getElements();
            if (!(elementSet == null)) {
                for (Element ele : elementSet) {
                    ele.setPage(page);

                    /*System.out.println("......ELE NAME : " + ele.getName());
                    System.out.println("......ELE TYPE : " + ele.getType());
                    System.out.println("......ELE ID : " + ele.getId());
                    System.out.println("......ELE QID : " + ele.getQuestionId());*/
                }
            }

        }

        return futureSurveyRepository.save(futureSurvey);
    }

    @Override
    public org.springframework.data.domain.Page<FutureSurvey> search(FutureSurveySearchCriteria criteria) {

        PageRequest page = PageRequest.of(criteria.getPageNumber() - 1, criteria.getPageSize(),
                Sort.Direction.fromString(criteria.getSortDirection() == null ? null : criteria.getSortDirection()), criteria.getSortProperty());

        org.springframework.data.domain.Page<FutureSurvey> futureSurveys = null;

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        if (StringUtils.isNotBlank(criteria.getName())) {
            booleanBuilder.and(QFutureSurvey.futureSurvey.title.equalsIgnoreCase(criteria.getTitle()));
        }

        if (booleanBuilder.hasValue()) {
            futureSurveys = futureSurveyRepository.findAll(booleanBuilder, page);
        } else {
            futureSurveys = futureSurveyRepository.findAll(page);
        }

        return futureSurveys;
    }

    @Override
    public FutureSurvey update(FutureSurvey futureSurvey) {

        FutureSurvey fSurvey = null;
        try {
            fSurvey = futureSurveyRepository.getOne(futureSurvey.getId());
        } catch (EntityNotFoundException e) {
            throw new ComplexValidationException("future-survey", "futureSurveytUpdateRequest.futureSurveNotExist");
        }

        fSurvey.setJsonContent(futureSurvey.getJsonContent());
        fSurvey.setTitle(futureSurvey.getTitle());
        fSurvey.setClientId(futureSurvey.getClientId());


        Set<Page> pages = futureSurvey.getPages();

        if (pages != null && !pages.isEmpty()) {

            for (Page page : pages) {
                page.setFutureSurvey(futureSurvey);
                Set<Element> elementSet = page.getElements();
                if (!(elementSet == null)) {
                    for (Element ele : elementSet) {
                        ele.setPage(page);
                    }
                }
            }

            Set<Page> currentSurveyPages = fSurvey.getPages();
            currentSurveyPages.clear();
            currentSurveyPages.addAll(pages);
            fSurvey.setPages(currentSurveyPages);
        }


        return futureSurveyRepository.save(fSurvey);
    }

    @Override
    public FutureSurvey delete(Long id) {
        FutureSurvey fSurvey = null;
        try {
            fSurvey = futureSurveyRepository.getOne(id);
        } catch (EntityNotFoundException e) {
            throw new ComplexValidationException("future-survey", "futureSurveyDeleteRequest.futureSurveyNotExist");
        }

        futureSurveyRepository.delete(fSurvey);
        return fSurvey;
    }


}
