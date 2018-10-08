package com.clearpicture.platform.product.controller;

import com.clearpicture.platform.exception.ComplexValidationException;
import com.clearpicture.platform.modelmapper.ModelMapper;
import com.clearpicture.platform.product.dto.response.ProductCreateResponse;
import com.clearpicture.platform.product.dto.response.ProductSearchResponse;
import com.clearpicture.platform.product.dto.response.ProductSuggestionResponse;
import com.clearpicture.platform.product.dto.response.ProductUpdateResponse;
import com.clearpicture.platform.product.entity.Client;
import com.clearpicture.platform.product.entity.Product;
import com.clearpicture.platform.product.entity.criteria.ProductSearchCriteria;
import com.clearpicture.platform.product.service.ClientService;
import com.clearpicture.platform.product.service.FileStorageService;
import com.clearpicture.platform.product.service.ProductService;
import com.clearpicture.platform.response.wrapper.ListResponseWrapper;
import com.clearpicture.platform.response.wrapper.PagingListResponseWrapper;
import com.clearpicture.platform.response.wrapper.SimpleResponseWrapper;
import com.clearpicture.platform.service.CryptoService;
import com.clearpicture.platform.product.dto.request.ProductCreateRequest;
import com.clearpicture.platform.product.dto.request.ProductSearchRequest;
import com.clearpicture.platform.product.dto.request.ProductUpdateRequest;
import com.clearpicture.platform.product.dto.response.ProductDeleteResponse;
import com.clearpicture.platform.product.dto.response.ProductViewResponse;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import java.io.IOException;
import java.time.LocalDate;
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

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private ClientService clientService;

    @PostMapping(value = "${app.endpoint.productsCreate}")
    public ResponseEntity<SimpleResponseWrapper<ProductCreateResponse>> create(
            @RequestParam(value = "file",required = false)MultipartFile file ,@RequestParam("code") String code,
            @RequestParam(value = "quantity")String quantity ,@RequestParam(value = "expireDate",required = false) String expireDate,
            @RequestParam(value = "name",required = false)String name ,@RequestParam(value = "description",required = false) String description,
            @RequestParam(value = "batchNumber",required = false)String batchNumber ,@RequestParam(value = "client") String client,@RequestParam(value = "surveyId",required = false) String surveyId) throws IOException, ServletException {

        ProductCreateRequest request = new ProductCreateRequest();
        request.setCode(code);
        request.setName(name);
        request.setDescription(description);
        request.setQuantity(quantity);
        request.setExpireDate(expireDate != null ? LocalDate.parse(expireDate) : null);
        request.setBatchNumber(batchNumber);
        if(surveyId != null) {
            Boolean isValidSurvey = productService.validateSurvey(surveyId);
            if (!isValidSurvey) {
                throw new ComplexValidationException("surveyId", "productsCreateRequest.surveyId.invalid");
            }
            request.setSurveyId(surveyId);
        }
        if(file != null) {
            request.setImageName(file.getOriginalFilename());
            request.setImageObject(fileStorageService.storeFile(file));
        }


        ProductCreateRequest.ClientData clientData = new ProductCreateRequest.ClientData();
        Client dbClient = clientService.retrieve(cryptoService.decryptEntityId(client));
        if(dbClient == null) {
            throw  new ComplexValidationException("client","productsCreateRequest.client.invalid");
        }
        clientData.setId(client);
        request.setClient(clientData);

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
    public ResponseEntity<SimpleResponseWrapper<ProductUpdateResponse>> update(@PathVariable String id,
                                                                               @RequestParam(value = "file",required = false)MultipartFile file ,@RequestParam("code") String code,
                                                                               @RequestParam(value = "quantity")String quantity ,@RequestParam(value = "expireDate",required = false) String expireDate,
                                                                               @RequestParam(value = "name",required = false)String name ,@RequestParam(value = "description",required = false) String description,
                                                                               @RequestParam(value = "batchNumber",required = false)String batchNumber ,@RequestParam(value = "client") String client,@RequestParam(value = "surveyId",required = false) String surveyId) throws IOException, ServletException {

        Long productId = cryptoService.decryptEntityId(id);

        ProductUpdateRequest request = new ProductUpdateRequest();
        request.setCode(code);
        request.setName(name);
        request.setDescription(description);
        request.setQuantity(quantity);
        request.setExpireDate(expireDate != null ? LocalDate.parse(expireDate) : null);
        request.setBatchNumber(batchNumber);
        if(surveyId != null) {
            Boolean isValidSurvey = productService.validateSurvey(surveyId);
            if (!isValidSurvey) {
                throw new ComplexValidationException("surveyId", "productsCreateRequest.surveyId.invalid");
            }
            request.setSurveyId(surveyId);
        }
        if(file != null) {
            request.setImageName(file.getOriginalFilename());
            request.setImageObject(fileStorageService.storeFile(file));
        }

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
    public ResponseEntity<ListResponseWrapper<ProductSuggestionResponse>> retrieveSuggestion() {

        //Long productId = cryptoService.decryptEntityId(id);

        //After aouth implementation we can use retrieve logged use as client
        List<Product> products = productService.retrieveForSuggestions();

        List<ProductSuggestionResponse> productSuggestions = modelMapper.map(products, ProductSuggestionResponse.class);

        return new ResponseEntity<ListResponseWrapper<ProductSuggestionResponse>>(
                new ListResponseWrapper<ProductSuggestionResponse>(productSuggestions), HttpStatus.OK);
    }
}
