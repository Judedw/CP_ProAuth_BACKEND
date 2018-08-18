package com.clearpicture.platform.survey.service.impl;

import com.clearpicture.platform.survey.entity.Answer;
import com.clearpicture.platform.survey.entity.AnswerTemplate;
import com.clearpicture.platform.survey.entity.QAnswerTemplate;
import com.clearpicture.platform.survey.entity.QQuestion;
import com.clearpicture.platform.survey.entity.criteria.AnswerTemplateCriteria;
import com.clearpicture.platform.survey.enums.AnswerTemplateStatus;
import com.clearpicture.platform.survey.enums.QuestionStatus;
import com.clearpicture.platform.survey.repository.AnswerRepository;
import com.clearpicture.platform.survey.repository.AnswerTemplateRepository;
import com.clearpicture.platform.survey.service.AnswerTemplateService;
import com.querydsl.core.BooleanBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * AnswerTemplateServiceImpl
 * Created by nuwan on 8/18/18.
 */
@Transactional
@Service("answerTemplateService")
public class AnswerTemplateServiceImpl implements AnswerTemplateService {

    @Autowired
    private AnswerTemplateRepository answerTemplateRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Override
    public AnswerTemplate create(AnswerTemplate newAnswerTemplate) {

        newAnswerTemplate.setStatus(AnswerTemplateStatus.ACTIVE);

        for (Answer answer : newAnswerTemplate.getAnswers()) {
            if (!(answer.getLable() == "")) {
                answer.setAnswerTemplate(newAnswerTemplate);
            } else {
                newAnswerTemplate.getAnswers().remove(answer);
            }
        }

        return answerTemplateRepository.save(newAnswerTemplate);

    }

    @Transactional(readOnly = true)
    @Override
    public Page<AnswerTemplate> search(AnswerTemplateCriteria criteria) {
        PageRequest page = PageRequest.of(criteria.getPageNumber() - 1, criteria.getPageSize(),
                Sort.Direction.fromString(criteria.getSortDirection() == null ? null : criteria.getSortDirection()), criteria.getSortProperty());
        Page<AnswerTemplate> answerTemplates = null;

        BooleanBuilder booleanBuilder = new BooleanBuilder(
                QAnswerTemplate.answerTemplate.status.ne(AnswerTemplateStatus.DELETED));

        if (StringUtils.isNotBlank(criteria.getKeyword())) {
            booleanBuilder.and(QAnswerTemplate.answerTemplate.name.containsIgnoreCase(criteria.getKeyword()));
        }

        if (booleanBuilder.hasValue()) {
            answerTemplates = answerTemplateRepository.findAll(booleanBuilder, page);
        } else {
            answerTemplates = answerTemplateRepository.findAll(page);
        }

        return answerTemplates;
    }

    @Override
    public AnswerTemplate retrieve(Long id) {
        return answerTemplateRepository.getOne(id);
    }

    @Override
    public AnswerTemplate update(AnswerTemplate answerTemplate) {
        AnswerTemplate currentAnswerTemplate = answerTemplateRepository.getOne(answerTemplate.getId());

        /*if (currentAnswerTemplate == null) {
            throw new ComplexValidationException("answerTemplate",
                    "answerTemplateUpdateRequest.answerTemplateNotExist");
        }*/

        BooleanBuilder booleanBuilder = new BooleanBuilder(QAnswerTemplate.answerTemplate.questions.isNotEmpty())
                .and(QAnswerTemplate.answerTemplate.id.eq(answerTemplate.getId()));

        /*if (answerTemplateRepository.exists(booleanBuilder)) {
            throw new ComplexValidationException("answerTemplate",
                    "answerTemplateUpdateRequest.answerTemplateAttachedToQuestions");
        }*/

        currentAnswerTemplate.setName(answerTemplate.getName());
        currentAnswerTemplate.setAnswerTemplateType(answerTemplate.getAnswerTemplateType());
        currentAnswerTemplate.setReverseDisplay(answerTemplate.getReverseDisplay());

        Set<Answer> answers = new HashSet();
        boolean newadded=false;

		/* iterating all answers from database */
        for (Answer currentAnswer : currentAnswerTemplate.getAnswers()) {
            boolean available = false;

			/* looking for matching answer entity from request */
            for (Answer answer : answerTemplate.getAnswers()) {
				/* set values if matching answer founds */
                if (currentAnswer.getId() == answer.getId()) {
                    Answer currentanswer = answerRepository.getOne(answer.getId());
                    currentanswer.setLable(answer.getLable());
                    currentanswer.setValue(answer.getValue());
                    currentanswer.setOptionNumber(answer.getOptionNumber());
                    available = true;
                    answers.add(currentanswer);
                }
				/* new answers save only one time */
                if(answer.getId()==null && newadded==false) {
                    answer.setAnswerTemplate(currentAnswerTemplate);
                    answerRepository.save(answer);
                }

            }
            newadded=true;
			/*
			 * if database has miss matching answers with request delete them from database
			 */
            if (!available) {
                answerRepository.delete(currentAnswer);
            }

        }

        currentAnswerTemplate.setAnswers(answers);
        AnswerTemplate dbAnswerTemplate = answerTemplateRepository.save(currentAnswerTemplate);

        return dbAnswerTemplate;
    }

    @Override
    public AnswerTemplate delete(Long id) {
        BooleanBuilder builder = new BooleanBuilder(QAnswerTemplate.answerTemplate.id.eq(id))
                .and(QAnswerTemplate.answerTemplate.answers.isNotEmpty());

        BooleanBuilder booleanBuilderQuestion = new BooleanBuilder(QQuestion.question.status.ne(QuestionStatus.DELETED))
                .and(QQuestion.question.answerTemplate.id.eq(id));

        /*if (questionRepository.exists(booleanBuilderQuestion)) {
            throw new ComplexValidationException("answerTemplate",
                    "answerTemplateUpdateRequest.answerTemplateAttachedToQuestions");
        }*/

        AnswerTemplate currentAnswerTemplate = answerTemplateRepository.getOne(id);
        /*if (currentAnswerTemplate == null) {
            throw new ComplexValidationException("answerTemplate",
                    "answerTemplateDeleteRequest.answerTemplateNotExist");
        }*/

        currentAnswerTemplate.setStatus(AnswerTemplateStatus.DELETED);
        answerTemplateRepository.save(currentAnswerTemplate);

        return currentAnswerTemplate;
    }


    @Transactional(readOnly = true)
    @Override
    public List<AnswerTemplate> retrieveForSuggestions(String keyword) {

        if (StringUtils.isNotBlank(keyword)) {
            BooleanBuilder booleanBuilder = new BooleanBuilder(
                    QAnswerTemplate.answerTemplate.name.containsIgnoreCase(keyword));
            return (List<AnswerTemplate>) answerTemplateRepository.findAll(booleanBuilder);
        } else {
            BooleanBuilder booleanBuilder = new BooleanBuilder(
                    QAnswerTemplate.answerTemplate.status.ne(AnswerTemplateStatus.DELETED));
            return (List<AnswerTemplate>) answerTemplateRepository.findAll(booleanBuilder);
        }
    }
}
