package com.clearpicture.platform.survey.repository;

import com.clearpicture.platform.survey.entity.AnswerTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

/**
 * AnswerTemplateRepository
 * Created by nuwan on 8/18/18.
 */
@Repository
public interface AnswerTemplateRepository extends JpaRepository<AnswerTemplate,Long>,QuerydslPredicateExecutor<AnswerTemplate> {
}
