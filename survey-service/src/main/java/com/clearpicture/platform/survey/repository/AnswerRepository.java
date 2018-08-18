package com.clearpicture.platform.survey.repository;

import com.clearpicture.platform.survey.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

/**
 * AnswerRepository
 * Created by nuwan on 8/18/18.
 */
@Repository
public interface AnswerRepository extends JpaRepository<Answer,Long>,QuerydslPredicateExecutor<Answer> {
}
