package com.clearpicture.platform.product.repository;

import com.clearpicture.platform.product.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

/**
 * ClientRepository
 * Created by nuwan on 7/21/18.
 */
@Repository
public interface ClientRepository extends JpaRepository<Client,Long>,QuerydslPredicateExecutor<Client> {

}
