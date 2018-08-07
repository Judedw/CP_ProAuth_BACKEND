package com.product.genuine.service;

import com.product.genuine.entity.Product;
import com.product.genuine.entity.criteria.ProductSearchCriteria;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * ProductService
 * Created by nuwan on 7/12/18.
 */
public interface ProductService {

    Product save(Product product);

    Page<Product> search(ProductSearchCriteria criteria);

    Product retrieve(Long aLong);

    Product update(Product product);

    Product delete(Long aLong);

    List<Product> retrieveForSuggestions(Long id);
}
