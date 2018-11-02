package com.clearpicture.platform.product.service.impl;

import com.clearpicture.platform.exception.ComplexValidationException;
import com.clearpicture.platform.product.entity.*;
import com.clearpicture.platform.product.entity.criteria.ProductSearchCriteria;
import com.clearpicture.platform.product.repository.ClientRepository;
import com.clearpicture.platform.product.repository.ProductDetailRepository;
import com.clearpicture.platform.product.repository.ProductImageRepository;
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
    private ProductImageRepository imageRepository;

    @Autowired
    private ClientService clientService;

    @Autowired
    private Ms2msCommunicationService ms2msCommunicationService;

    @Override
    public Product save(Product product) {

        try {
            //Client client = clientRepository.getOne(product.getClient().getId());
            Client client = clientService.retrieve(product.getClient().getId());
            log.debug("{}", client);
            System.out.println(client);//do not removed this
        } catch (EntityNotFoundException e) {
            throw new ComplexValidationException("client", "productCreateRequest.clientNotExist");
        } catch (Exception e) {
            throw new ComplexValidationException("client", "productCreateRequest.clientNotExist");
        }


        Set<ProductDetail> productDetails = new HashSet<>();
        Long lastId = productDetailRepository.getMaxId();
        for (int i = 0; i < product.getQuantity(); i++) {
            ProductDetail productDetail = new ProductDetail();
            productDetail.setUniqueProductCode(product.getCode() + "/" + product.getClient().getId() + "/" + lastId++);
            productDetail.setProduct(product);
            productDetails.add(productDetail);
        }

        Set<ProductImage> prodImages = product.getImageObjects();

        if (prodImages != null && !prodImages.isEmpty()) {
            for (ProductImage image : product.getImageObjects()) {
                image.setProduct(product);
            }
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

        if (StringUtils.isNotBlank(criteria.getName())) {
            booleanBuilder.and(QProduct.product.code.equalsIgnoreCase(criteria.getCode()));

        }

        if (booleanBuilder.hasValue()) {
            products = productRepository.findAll(booleanBuilder, page);
        } else {
            products = productRepository.findAll(page);
        }

        if (products != null && products.getSize() != 0) {
            products.forEach(product -> {
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
            throw new ComplexValidationException("product", "productViewRequest.productNotExist");
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
            currentProduct.setName(product.getName());

            // Currently existing image objects in database
            Set<ProductImage> productImages = currentProduct.getImageObjects();

            // Image id set that is not going to be change
            Set<Long> remainImageIds = product.getRemainImagesID();

            // Declare and Initialize a set for newly added images
            Set<ProductImage> toBeUpdatedSet = new HashSet<>();

            if (remainImageIds != null && !remainImageIds.isEmpty()) {
                productImages.forEach(image -> {
                    if (remainImageIds.contains(image.getId())) {
                        toBeUpdatedSet.add(image);
                    }
                });
            }

            // Incoming newly added Images
            Set<ProductImage> incomingImages = product.getImageObjects();

            if (incomingImages != null && !incomingImages.isEmpty()) {
                // setting up the product object to new product image objects
                for (ProductImage image : product.getImageObjects()) {
                    image.setProduct(product);
                }

                // add incoming new images to to be updated image list.
                toBeUpdatedSet.addAll(incomingImages);
            }

            // clear the existing set in object level
            currentProduct.getImageObjects().clear();

            // add new updated Set into current product object
            currentProduct.getImageObjects().addAll(toBeUpdatedSet);

            return productRepository.save(currentProduct);

        } catch (EntityNotFoundException e) {
            throw new ComplexValidationException("product", "productUpdateRequest.productNotExist");
        }

    }

    @Override
    public Product delete(Long id) {
        Product product = null;
        try {
            product = productRepository.getOne(id);
            product.getProductDetails().size();
        } catch (EntityNotFoundException e) {
            throw new ComplexValidationException("product", "productViewRequest.productNotExist");
        }
        productRepository.delete(product);
        return product;

    }

    @Override
    public List<Product> retrieveForSuggestions() {
        List<Product> products = productRepository.findAll();
        return products;


    }

    @Override
    public Boolean validateSurvey(String surveyId) {
        return ms2msCommunicationService.validateSurvey(surveyId);
    }

    @Override
    public List<ProductImage> retrieveImages(Long productId) {

        BooleanBuilder booleanBuilder = new BooleanBuilder(QProductImage.productImage.product.id.eq(productId));
        return (List<ProductImage>) imageRepository.findAll(booleanBuilder);

    }

    @Override
    public ProductImage retrieveProductImage(Long productImageId) {
        try {
            return imageRepository.getOne(productImageId);
        } catch (EntityNotFoundException e) {
            throw new ComplexValidationException("product_image", "productImageRetrieve.producImageNotExists");
        }

    }
}
