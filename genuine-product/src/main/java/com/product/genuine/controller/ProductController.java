package com.product.genuine.controller;

import com.product.genuine.dto.request.ProductCreateRequest;
import com.product.genuine.dto.request.ProductSearchRequest;
import com.product.genuine.dto.request.ProductUpdateRequest;
import com.product.genuine.dto.response.ProductCreateResponse;
import com.product.genuine.dto.response.ProductDeleteResponse;
import com.product.genuine.dto.response.ProductSearchResponse;
import com.product.genuine.dto.response.ProductSuggestionResponse;
import com.product.genuine.dto.response.ProductUpdateResponse;
import com.product.genuine.dto.response.ProductViewResponse;
import com.product.genuine.dto.response.wrapper.ListResponseWrapper;
import com.product.genuine.dto.response.wrapper.PagingListResponseWrapper;
import com.product.genuine.dto.response.wrapper.SimpleResponseWrapper;
import com.product.genuine.entity.Product;
import com.product.genuine.entity.criteria.ProductSearchCriteria;
import com.product.genuine.modelmapper.ModelMapper;
import com.product.genuine.service.CryptoService;
import com.product.genuine.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * ProductController
 * Created by nuwan on 7/12/18.
 * (C) Copyright 2018 nuwan (http://www.efuturesworld.com/) and others.
 */
@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CryptoService cryptoService;

    @PostMapping("${app.endpoint.productsCreate}")
    public ResponseEntity<SimpleResponseWrapper<ProductCreateResponse>> create(@Validated @RequestBody ProductCreateRequest request) {

        Product product = modelMapper.map(request,Product.class);
        Product saveProduct = productService.save(product);
        ProductCreateResponse response = modelMapper.map(saveProduct,ProductCreateResponse.class);

        return new ResponseEntity<SimpleResponseWrapper<ProductCreateResponse>>(new SimpleResponseWrapper<>(response), HttpStatus.CREATED);
    }

    @GetMapping("${app.endpoint.productsSearch}")
    public ResponseEntity<PagingListResponseWrapper<ProductSearchResponse>> retrieve(@Validated ProductSearchRequest request) {

        ProductSearchCriteria criteria = modelMapper.map(request,ProductSearchCriteria.class);

        Page<Product> results = productService.search(criteria);

        List<ProductSearchResponse> products =  modelMapper.map(results.getContent(),ProductSearchResponse.class);

        PagingListResponseWrapper.Pagination pagination = new PagingListResponseWrapper.Pagination(
                results.getNumber() + 1, results.getSize(), results.getTotalPages(), results.getTotalElements());

        return new ResponseEntity<PagingListResponseWrapper<ProductSearchResponse>>(new PagingListResponseWrapper<ProductSearchResponse>(products,pagination),HttpStatus.OK);
    }

    @GetMapping("${app.endpoint.productsView}")
    public ResponseEntity<SimpleResponseWrapper<ProductViewResponse>> view(@PathVariable String id) {

        Long productId = cryptoService.decryptEntityId(id);

        Product retrievedProduct = productService.retrieve(productId);

        ProductViewResponse response = modelMapper.map(retrievedProduct,ProductViewResponse.class);

        return new ResponseEntity<SimpleResponseWrapper<ProductViewResponse>>(new SimpleResponseWrapper<ProductViewResponse>(response),HttpStatus.OK);


    }

    @PutMapping("${app.endpoint.productsUpdate}")
    public ResponseEntity<SimpleResponseWrapper<ProductUpdateResponse>> update(@PathVariable String id,@Validated @RequestBody ProductUpdateRequest request) {
        Long productId = cryptoService.decryptEntityId(id);

        Product product = modelMapper.map(request,Product.class);

        product.setId(productId);

        Product updatedClient = productService.update(product);

        ProductUpdateResponse response = modelMapper.map(updatedClient,ProductUpdateResponse.class);

        return new ResponseEntity<SimpleResponseWrapper<ProductUpdateResponse>>(new SimpleResponseWrapper<ProductUpdateResponse>(response),HttpStatus.OK);


    }

    @DeleteMapping("${app.endpoint.productsDelete}")
    public ResponseEntity<SimpleResponseWrapper<ProductDeleteResponse>> delete(@PathVariable String id) {

        Long productId = cryptoService.decryptEntityId(id);

        Product client = productService.delete(productId);

        ProductDeleteResponse response = modelMapper.map(client.getId(),ProductDeleteResponse.class);

        return new ResponseEntity<SimpleResponseWrapper<ProductDeleteResponse>>(new SimpleResponseWrapper<ProductDeleteResponse>(response),HttpStatus.OK);

    }

    @GetMapping("${app.endpoint.productsSuggestion}")
    public ResponseEntity<ListResponseWrapper<ProductSuggestionResponse>> retrieve(@PathVariable String id) {

        Long productId = cryptoService.decryptEntityId(id);

        List<Product> products = productService.retrieveForSuggestions(productId);

        List<ProductSuggestionResponse> productSuggestions = modelMapper.map(products, ProductSuggestionResponse.class);

        return new ResponseEntity<ListResponseWrapper<ProductSuggestionResponse>>(
                new ListResponseWrapper<ProductSuggestionResponse>(productSuggestions), HttpStatus.OK);
    }
}
