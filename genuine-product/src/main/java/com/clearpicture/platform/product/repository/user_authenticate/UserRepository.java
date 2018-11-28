package com.clearpicture.platform.product.repository.user_authenticate;

import com.clearpicture.platform.product.entity.user_authenticate.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

/**
 * UserRepository
 * Created by Buddhi on 11/28/18.
 */
@Repository
public interface UserRepository extends JpaRepository<User,Long>,QuerydslPredicateExecutor<User> {

}
