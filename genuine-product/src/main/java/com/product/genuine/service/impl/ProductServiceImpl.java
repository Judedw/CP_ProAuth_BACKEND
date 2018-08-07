package com.product.genuine.service.impl;

import com.product.genuine.entity.Product;
import com.product.genuine.entity.ProductDetail;
import com.product.genuine.entity.QProduct;
import com.product.genuine.entity.criteria.ProductSearchCriteria;
import com.product.genuine.repository.ProductDetailRepository;
import com.product.genuine.repository.ProductRepository;
import com.product.genuine.service.ProductService;
import com.querydsl.core.BooleanBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * ProductServiceImpl
 * Created by nuwan on 7/12/18.
 */
@Transactional
@Service("productService")
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductDetailRepository productDetailRepository;

    @Override
    public Product save(Product product) {

        Set<ProductDetail> productDetails = new HashSet<>();
        Long lastId = productDetailRepository.getMaxId();
        for(int i =0 ; i < product.getQuantity(); i++) {
            ProductDetail productDetail = new ProductDetail();
            productDetail.setUniqueProductCode(product.getCode()+"/"+product.getClient().getId()+"/"+lastId++);
            productDetail.setProduct(product);
            productDetails.add(productDetail);
        }

        product.setProductDetails(productDetails);

        return productRepository.save(product);
    }

    @Override
    public Page<Product> search(ProductSearchCriteria criteria) {

        PageRequest page = PageRequest.of(criteria.getPageNumber() - 1, criteria.getPageSize(),
                Sort.Direction.fromString(criteria.getSortDirection() == null ? null : criteria.getSortDirection()), criteria.getSortProperty());
        Page<Product> products = null;

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        if(StringUtils.isNotBlank(criteria.getName())) {
            booleanBuilder.and(QProduct.product.code.equalsIgnoreCase(criteria.getCode()));

        }

        if (booleanBuilder.hasValue()) {
            products = productRepository.findAll(booleanBuilder, page);
        } else {
            products = productRepository.findAll(page);
        }

        if(products != null && products.getSize() != 0) {
            products.forEach(product ->{
                product.getProductDetails().size();
            });
        }

        return products;

    }

    @Override
    public Product retrieve(Long id) {
        Product product = productRepository.getOne(id);
        if(product != null) {
            product.getProductDetails().size();
        }
        return product;
    }

    @Override
    public Product update(Product product) {
        Product currentProduct = productRepository.getOne(product.getId());

        if(currentProduct == null) {

        }

        currentProduct.setCode(product.getCode());
        currentProduct.setDescription(product.getDescription());
        currentProduct.setQuantity(product.getQuantity());
        currentProduct.setExpireDate(product.getExpireDate());
        currentProduct.setBatchNumber(product.getBatchNumber());

        return productRepository.save(product);
    }

    @Override
    public Product delete(Long id) {

        Product currentProduct = productRepository.getOne(id);

        if(currentProduct == null) {

        }

        //if product attached to product details to will not permit to delete client

        productRepository.delete(currentProduct);

        return currentProduct;
    }

    @Override
    public List<Product> retrieveForSuggestions(Long id) {
        BooleanBuilder booleanBuilder = new BooleanBuilder(QProduct.product.client.id.eq(id));

        return (List<Product>) productRepository.findAll(booleanBuilder);



    }
}
