package com.clearpicture.platform.product.service.impl;

import com.clearpicture.platform.exception.ComplexValidationException;
import com.clearpicture.platform.product.entity.Client;
import com.clearpicture.platform.product.entity.Product;
import com.clearpicture.platform.product.entity.ProductDetail;
import com.clearpicture.platform.product.entity.QProduct;
import com.clearpicture.platform.product.entity.criteria.ProductSearchCriteria;
import com.clearpicture.platform.product.repository.ClientRepository;
import com.clearpicture.platform.product.repository.ProductDetailRepository;
import com.clearpicture.platform.product.repository.ProductRepository;
import com.clearpicture.platform.product.service.ClientService;
import com.clearpicture.platform.product.service.Ms2msCommunicationService;
import com.clearpicture.platform.product.service.ProductService;
import com.querydsl.core.BooleanBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * ProductServiceImpl
 * Created by nuwan on 7/12/18.
 */
@Slf4j
@Transactional
@Service("productService")
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductDetailRepository productDetailRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ClientService clientService;

    @Autowired
    private Ms2msCommunicationService ms2msCommunicationService;

    @Override
    public Product save(Product product) {

        try {
            //Client client = clientRepository.getOne(product.getClient().getId());
            Client client = clientService.retrieve(product.getClient().getId());
            log.debug("{}",client);
            System.out.println(client);//do not removed this
        } catch (EntityNotFoundException e) {
            throw new ComplexValidationException("client", "productCreateRequest.clientNotExist");
        } catch (Exception e) {
            throw new ComplexValidationException("client", "productCreateRequest.clientNotExist");
        }


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
        try {
            Product product = productRepository.getOne(id);
            product.getProductDetails().size();
            return product;
        } catch (EntityNotFoundException e) {
            throw new ComplexValidationException("product", "productCreateRequest.productNotExist");
        }

    }

    @Override
    public Product update(Product product) {

        try {
            Product currentProduct = productRepository.getOne(product.getId());
            currentProduct.setCode(product.getCode());
            currentProduct.setDescription(product.getDescription());
            currentProduct.setQuantity(product.getQuantity());
            currentProduct.setExpireDate(product.getExpireDate());
            currentProduct.setBatchNumber(product.getBatchNumber());
            currentProduct.setClient(product.getClient());
            currentProduct.setSurveyId(product.getSurveyId());

            return productRepository.save(currentProduct);

        } catch (EntityNotFoundException e) {
            throw new ComplexValidationException("product", "productUpdateRequest.productNotExist");
        }

    }

    @Override
    public Product delete(Long id) {

        try {
            Product currentProduct = productRepository.getOne(id);
            productRepository.delete(currentProduct);
            return currentProduct;
        } catch (EntityNotFoundException e) {
            throw new ComplexValidationException("product", "productDeleteRequest.productNotExist");
        }

    }

    @Override
    public List<Product> retrieveForSuggestions() {
        //BooleanBuilder booleanBuilder = new BooleanBuilder();

        List<Product> products = productRepository.findAll();

        return products;



    }

    @Override
    public Boolean validateSurvey(String surveyId) {
        return ms2msCommunicationService.validateSurvey(surveyId);
    }
}
