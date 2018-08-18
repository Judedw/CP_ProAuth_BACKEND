package com.clearpicture.platform.product.repository;

import com.clearpicture.platform.product.entity.UserKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

/**
 * UserKeyRepository
 * Created by nuwan on 8/4/18.
 */
@Repository
public interface UserKeyRepository extends JpaRepository<UserKey,Long>,QuerydslPredicateExecutor<UserKey> {
    UserKey findByAppId(String appId);
}
