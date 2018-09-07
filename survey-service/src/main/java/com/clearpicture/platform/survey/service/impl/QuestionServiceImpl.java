package com.clearpicture.platform.survey.service.impl;

import com.clearpicture.platform.exception.ComplexValidationException;
import com.clearpicture.platform.survey.entity.AnswerTemplate;
import com.clearpicture.platform.survey.entity.QQuestion;
import com.clearpicture.platform.survey.entity.Question;
import com.clearpicture.platform.survey.entity.criteria.QuestionCriteria;
import com.clearpicture.platform.survey.enums.QuestionStatus;
import com.clearpicture.platform.survey.repository.QuestionRepository;
import com.clearpicture.platform.survey.service.AnswerTemplateService;
import com.clearpicture.platform.survey.service.QuestionService;
import com.querydsl.core.BooleanBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * QuestionServiceImpl
 * Created by nuwan on 8/17/18.
 */
@Transactional
@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerTemplateService answerTemplateService;

    @Override
    public Question create(Question newQuestion) {

        if(newQuestion.getAnswerTemplate() != null ) {
            AnswerTemplate answerTemplate = answerTemplateService.retrieve(newQuestion.getAnswerTemplate().getId());
            newQuestion.setAnswerTemplate(answerTemplate);
        }

        newQuestion.setStatus(QuestionStatus.NEW);
        Question persistedQuestion = questionRepository.save(newQuestion);

        return persistedQuestion;

    }

    @Transactional(readOnly=true)
    @Override
    public Page<Question> search(QuestionCriteria criteria) {

        PageRequest page = PageRequest.of(criteria.getPageNumber() - 1, criteria.getPageSize(),
                Sort.Direction.fromString(criteria.getSortDirection() == null ? null : criteria.getSortDirection()), criteria.getSortProperty());
        Page<Question> questions = null;
        BooleanBuilder booleanBuilder = new BooleanBuilder();

		if(StringUtils.isNotBlank(criteria.getKeyword())) {
			booleanBuilder.and(QQuestion.question.name.containsIgnoreCase(criteria.getKeyword()));
		}
        if (booleanBuilder.hasValue()) {
			questions = questionRepository.findAll(booleanBuilder, page);
		} else {
			questions = questionRepository.findAll(page);
		}
        return questions;
    }

    @Transactional(readOnly=true)
    @Override
    public Question retrieve(Long questionId) {
        Question question = questionRepository.getOne(questionId);

        /*if (question == null) {
            throw new ComplexValidationException("question", "questionViewRequest.questionNotExist");
        }*/

        return question;
    }

    @Override
    public Question update(Question question) {
        Question currentQuestion =questionRepository.getOne(question.getId());

        if(currentQuestion == null) {
            throw new ComplexValidationException("question", "questionUpdateRequest.questionNotExist");
        }

        currentQuestion.setName(question.getName());

        if(question.getAnswerTemplate() != null ) {
            AnswerTemplate answerTemplate = answerTemplateService.retrieve(question.getAnswerTemplate().getId());
            currentQuestion.setAnswerTemplate(answerTemplate);
        }


        Question persistedQuestion = questionRepository.save(currentQuestion);

        return persistedQuestion;
    }

    @Override
    public Question delete(Long questionId) {
        Question question = questionRepository.getOne(questionId);
        questionRepository.delete(question);
        return question;

        /*if (question == null) {
            //throw new ComplexValidationException("question", "questionDeleteRequest.questionNotExist");
        } else {
            question.setStatus(QuestionStatus.DELETED);
            Question persistedQuestion = questionRepository.save(question);

            return persistedQuestion;
        }*/

    }

}
