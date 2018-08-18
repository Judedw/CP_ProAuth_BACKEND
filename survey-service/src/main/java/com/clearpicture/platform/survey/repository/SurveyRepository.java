package com.clearpicture.platform.survey.repository;

import com.clearpicture.platform.survey.entity.Survey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

/**
 * SurveyRepository
 * Created by nuwan on 8/18/18.
 */
@Repository
public interface SurveyRepository extends JpaRepository<Survey,Long>,QuerydslPredicateExecutor<Survey> {
}
