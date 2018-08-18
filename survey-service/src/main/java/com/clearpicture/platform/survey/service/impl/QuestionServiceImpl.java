package com.clearpicture.platform.survey.service.impl;

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

        newQuestion.setStatus(QuestionStatus.ACTIVE);
        Question persistedQuestion = questionRepository.save(newQuestion);

        return persistedQuestion;

    }

    @Transactional(readOnly=true)
    @Override
    public Page<Question> search(QuestionCriteria criteria) {

        PageRequest page = PageRequest.of(criteria.getPageNumber() - 1, criteria.getPageSize(),
                Sort.Direction.fromString(criteria.getSortDirection() == null ? null : criteria.getSortDirection()), criteria.getSortProperty());
        Page<Question> questions = null;
        BooleanBuilder booleanBuilder = new BooleanBuilder(QQuestion.question.status.ne(QuestionStatus.DELETED));

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
        Question currentQestion =questionRepository.getOne(question.getId());

        /*if(currentQestion == null) {
            throw new ComplexValidationException("question", "questionUpdateRequest.questionNotExist");
        }*/

        currentQestion.setName(question.getName());

        if(question.getAnswerTemplate() != null ) {
            AnswerTemplate answerTemplate = answerTemplateService.retrieve(question.getAnswerTemplate().getId());
            currentQestion.setAnswerTemplate(answerTemplate);
        }


        Question persistedQuestion = questionRepository.save(currentQestion);

        return persistedQuestion;
    }

    @Override
    public Question delete(Long questionId) {
        Question question = questionRepository.getOne(questionId);
        question.setStatus(QuestionStatus.DELETED);
        Question persistedQuestion = questionRepository.save(question);

        return persistedQuestion;

        /*if (question == null) {
            //throw new ComplexValidationException("question", "questionDeleteRequest.questionNotExist");
        } else {
            question.setStatus(QuestionStatus.DELETED);
            Question persistedQuestion = questionRepository.save(question);

            return persistedQuestion;
        }*/

    }

}
