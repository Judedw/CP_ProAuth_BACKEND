package com.clearpicture.platform.survey.repository;

import com.clearpicture.platform.survey.entity.FutureSurvey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

/**
 * created by Raveen -  18/10/24
 * FutureSurveyController - Handling every advanced surveys & e votes
 */

public interface FutureSurveyRepository extends JpaRepository<FutureSurvey, Long>, QuerydslPredicateExecutor<FutureSurvey> {


}
