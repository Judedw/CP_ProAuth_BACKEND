package com.clearpicture.platform.survey.repository;

import com.clearpicture.platform.survey.entity.FutureSurveyAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface FutureSurveyAnswerRepository extends JpaRepository<FutureSurveyAnswer, Long>, QuerydslPredicateExecutor<FutureSurveyAnswer> {
}
