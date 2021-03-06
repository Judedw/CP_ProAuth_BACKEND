package com.clearpicture.platform.survey.repository;

import com.clearpicture.platform.survey.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

/**
 * QuestionRepository
 * Created by nuwan on 8/17/18.
 */
@Repository
public interface QuestionRepository extends JpaRepository<Question,Long>,QuerydslPredicateExecutor<Question> {

}
