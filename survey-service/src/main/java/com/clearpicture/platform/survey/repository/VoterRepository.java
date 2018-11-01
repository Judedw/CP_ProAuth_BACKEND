package com.clearpicture.platform.survey.repository;

import com.clearpicture.platform.survey.entity.Voter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

/**
 * VoterRepository
 * Created by nuwan on 9/6/18.
 */
public interface VoterRepository extends JpaRepository<Voter,Long>,QuerydslPredicateExecutor<Voter> {

    //@Query("SELECT v FROM Voter v where batchNumber =:batchNumber")
    List<Voter> findByBatchNumber(Integer batchNumber);

    List<Voter> findByBatchNumberAndClientId(Integer batchNumber, Long clientId);

}
