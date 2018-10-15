package com.clearpicture.platform.survey.service.impl;

import com.clearpicture.platform.exception.ComplexValidationException;
import com.clearpicture.platform.survey.entity.AnswerTemplate;
import com.clearpicture.platform.survey.entity.QSurvey;
import com.clearpicture.platform.survey.entity.Question;
import com.clearpicture.platform.survey.entity.Survey;
import com.clearpicture.platform.survey.entity.criteria.SurveySearchCriteria;
import com.clearpicture.platform.survey.enums.QuestionStatus;
import com.clearpicture.platform.survey.repository.AnswerTemplateRepository;
import com.clearpicture.platform.survey.repository.SurveyRepository;
import com.clearpicture.platform.survey.service.SurveyService;
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
import java.util.Optional;
import java.util.Set;

/**
 * SurveyServiceImpl
 * Created by nuwan on 8/18/18.
 */
@Transactional
@Service("surveyService")
public class SurveyServiceImpl implements SurveyService {

    @Autowired
    private SurveyRepository surveyRepository;

    @Autowired
    private AnswerTemplateRepository answerTemplateRepository;

    @Override
    public Survey save(Survey newSurvey) {
        if(newSurvey.getQuestions() != null && newSurvey.getQuestions().size() != 0) {
            for(Question question:newSurvey.getQuestions()) {
                question.setSurvey(newSurvey);
                question.setStatus(QuestionStatus.NEW);
                question.setExpirationDate(newSurvey.getEndDate());
            }
        }

        return surveyRepository.save(newSurvey);

    }

    @Transactional(readOnly = true)
    @Override
    public Page<Survey> search(SurveySearchCriteria criteria) {

        PageRequest page = PageRequest.of(criteria.getPageNumber() - 1, criteria.getPageSize(),
                Sort.Direction.fromString(criteria.getSortDirection() == null ? null : criteria.getSortDirection()), criteria.getSortProperty());
        Page<Survey> surveys = null;

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        if(StringUtils.isNotBlank(criteria.getTopic())) {
            booleanBuilder.and(QSurvey.survey.topic.containsIgnoreCase(criteria.getTopic()));

        }

        if (booleanBuilder.hasValue()) {
            surveys = surveyRepository.findAll(booleanBuilder, page);
        } else {
            surveys = surveyRepository.findAll(page);
        }

        if(surveys != null) {
            surveys.forEach(survey -> {
                survey.getQuestions().size();
            });
        }

        return surveys;

    }

    @Transactional(readOnly = true)
    @Override
    public Survey retrieve(Long surveyId) {

        try {
            Optional<Survey> survey = surveyRepository.findById(surveyId);
            if(survey != null) {
                return survey.get();
            } else {
                throw new ComplexValidationException("survey", "surveyUpdateRequest.surveyNotExist");
            }
        } catch (Exception e) {
            throw new ComplexValidationException("survey", "surveyUpdateRequest.surveyNotExist");
        }

    }

    @Override
    public Survey update(Survey survey) {

        Set<Question> newQuestions = new HashSet<>();
        Set<Question> deletedQuestions = new HashSet<>();

        try {
            Survey persistedSurvey = surveyRepository.getOne(survey.getId());
            persistedSurvey.setTopic(survey.getTopic());
            persistedSurvey.setType(survey.getType());
            persistedSurvey.setStartDate(survey.getStartDate());
            persistedSurvey.setEndDate(survey.getEndDate());
            persistedSurvey.setProductId(survey.getProductId());

            if(persistedSurvey.getQuestions() != null)
                persistedSurvey.getQuestions().size();

            if(persistedSurvey.getQuestions() != null && persistedSurvey.getQuestions().size() !=0) {

                if(survey.getQuestions() != null && survey.getQuestions().size() != 0) {

                    for(Question persistedQuestion:persistedSurvey.getQuestions()) {

                        boolean matchedQuestion = false;

                        for(Question updatedQuestion:survey.getQuestions()) {

                            if(updatedQuestion.getId() != null) {

                                if(persistedQuestion.getId().equals(updatedQuestion.getId())) {

                                    persistedQuestion.setName(updatedQuestion.getName());

                                    if(updatedQuestion.getAnswerTemplate() != null ) {
                                        AnswerTemplate answerTemplate = answerTemplateRepository.getOne(updatedQuestion.getAnswerTemplate().getId());
                                        persistedQuestion.setAnswerTemplate(answerTemplate);
                                    }
                                    matchedQuestion=true;
                                }

                            } else {
                                updatedQuestion.setSurvey(persistedSurvey);
                                newQuestions.add(updatedQuestion);
                            }
                        }

                        if(!matchedQuestion) {
                            deletedQuestions.add(persistedQuestion);
                        }

                    }
                } else {
                    deletedQuestions.addAll(persistedSurvey.getQuestions());
                }

            } else {
                if(survey.getQuestions() != null && survey.getQuestions().size() != 0) {
                    for(Question question:survey.getQuestions()) {
                        question.setSurvey(survey);
                    }
                    newQuestions.addAll(survey.getQuestions());
                }
            }
            persistedSurvey.getQuestions().removeAll(deletedQuestions);
            persistedSurvey.getQuestions().addAll(newQuestions);

            return surveyRepository.save(persistedSurvey);

        } catch (EntityNotFoundException e) {
            throw new ComplexValidationException("survey", "surveyUpdateRequest.surveyNotExist");
        }
    }

    @Override
    public Survey delete(Long surveyId) {
        Survey currentSurvey = surveyRepository.getOne(surveyId);
        surveyRepository.delete(currentSurvey);
        return currentSurvey;
    }
}
