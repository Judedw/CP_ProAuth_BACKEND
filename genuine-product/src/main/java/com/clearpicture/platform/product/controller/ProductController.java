package com.clearpicture.platform.product.controller;

import com.clearpicture.platform.exception.ComplexValidationException;
import com.clearpicture.platform.modelmapper.ModelMapper;
import com.clearpicture.platform.product.dto.response.ProductCreateResponse;
import com.clearpicture.platform.product.dto.response.ProductSearchResponse;
import com.clearpicture.platform.product.dto.response.ProductSuggestionResponse;
import com.clearpicture.platform.product.dto.response.ProductUpdateResponse;
import com.clearpicture.platform.product.entity.Client;
import com.clearpicture.platform.product.entity.Product;
import com.clearpicture.platform.product.entity.ProductImage;
import com.clearpicture.platform.product.entity.criteria.ProductSearchCriteria;
import com.clearpicture.platform.product.service.ClientService;
import com.clearpicture.platform.product.service.FileStorageService;
import com.clearpicture.platform.product.service.ProductService;
import com.clearpicture.platform.product.util.MediaTypeUtils;
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
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
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

    @Autowired
    private ServletContext servletContext;

    @PostMapping(value = "${app.endpoint.productsCreate}")
    public ResponseEntity<SimpleResponseWrapper<ProductCreateResponse>> create(
            @RequestParam(value = "file", required = false) List<MultipartFile> files, @RequestParam("code") String code,
            @RequestParam(value = "quantity") String quantity, @RequestParam(value = "expireDate", required = false) String expireDate,
            @RequestParam(value = "name", required = false) String name, @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "batchNumber", required = false) String batchNumber, @RequestParam(value = "client") String client, @RequestParam(value = "surveyId", required = false) String surveyId) throws IOException, ServletException {


        ProductCreateRequest request = new ProductCreateRequest();
        request.setCode(code);
        request.setName(name);
        request.setDescription(description);
        request.setQuantity(quantity);
        request.setExpireDate(expireDate != null ? LocalDate.parse(expireDate) : null);
        request.setBatchNumber(batchNumber);

        if (surveyId != null) {
            Boolean isValidSurvey = productService.validateSurvey(surveyId);
            if (!isValidSurvey) {
                throw new ComplexValidationException("surveyId", "productsCreateRequest.surveyId.invalid");
            }
            request.setSurveyId(surveyId);
        }
        /*if (file != null) {
            request.setImageName(file.getOriginalFilename());
            //request.setImageObject(fileStorageService.storeFile(file));
            request.setImageObject(file.getBytes());
        }*/

        if (!files.isEmpty()) {
            //In first phase we are limiting image count as 4
            List<ProductCreateRequest.ProductImageRequest> proImages = new ArrayList<>(4);
            for (MultipartFile file : files) {
                System.out.println("file name : " + file.getOriginalFilename());
                ProductCreateRequest.ProductImageRequest proImage = new ProductCreateRequest.ProductImageRequest();
                proImage.setImageName(file.getOriginalFilename());
                proImage.setImageObject(file.getBytes());
                proImages.add(proImage);
            }
            request.setImageObjects(proImages);
        }


        ProductCreateRequest.ClientData clientData = new ProductCreateRequest.ClientData();
        Client dbClient = clientService.retrieve(cryptoService.decryptEntityId(client));
        if (dbClient == null) {
            throw new ComplexValidationException("client", "productsCreateRequest.client.invalid");
        }
        clientData.setId(client);
        request.setClient(clientData);

        Product product = modelMapper.map(request, Product.class);
        Product saveProduct = productService.save(product);
        ProductCreateResponse response = modelMapper.map(saveProduct, ProductCreateResponse.class);

        return new ResponseEntity<SimpleResponseWrapper<ProductCreateResponse>>(new SimpleResponseWrapper<>(response), HttpStatus.CREATED);
    }

    @GetMapping("${app.endpoint.productsSearch}")
    public ResponseEntity<PagingListResponseWrapper<ProductSearchResponse>> retrieve(@Validated ProductSearchRequest request) {

        ProductSearchCriteria criteria = modelMapper.map(request, ProductSearchCriteria.class);

        Page<Product> results = productService.search(criteria);

        List<ProductSearchResponse> products = modelMapper.map(results.getContent(), ProductSearchResponse.class);

        PagingListResponseWrapper.Pagination pagination = new PagingListResponseWrapper.Pagination(
                results.getNumber() + 1, results.getSize(), results.getTotalPages(), results.getTotalElements());

        return new ResponseEntity<PagingListResponseWrapper<ProductSearchResponse>>(new PagingListResponseWrapper<ProductSearchResponse>(products, pagination), HttpStatus.OK);
    }

    @GetMapping("${app.endpoint.productsView}")
    public ResponseEntity<SimpleResponseWrapper<ProductViewResponse>> view(@PathVariable String id) {

        Long productId = cryptoService.decryptEntityId(id);

        Product retrievedProduct = productService.retrieve(productId);

        ProductViewResponse response = modelMapper.map(retrievedProduct, ProductViewResponse.class);

        return new ResponseEntity<SimpleResponseWrapper<ProductViewResponse>>(new SimpleResponseWrapper<ProductViewResponse>(response), HttpStatus.OK);


    }

    @PutMapping("${app.endpoint.productsUpdate}")
    public ResponseEntity<SimpleResponseWrapper<ProductUpdateResponse>> update(@PathVariable String id,
                                                                               @RequestParam(value = "file", required = false) List<MultipartFile> files, @RequestParam("code") String code,
                                                                               @RequestParam(value = "quantity") String quantity, @RequestParam(value = "expireDate", required = false) String expireDate,
                                                                               @RequestParam(value = "name", required = false) String name, @RequestParam(value = "description", required = false) String description,
                                                                               @RequestParam(value = "batchNumber", required = false) String batchNumber, @RequestParam(value = "client") String client,
                                                                               @RequestParam(value = "surveyId", required = false) String surveyId,
                                                                               @RequestParam(value = "remainImagesID", required = false) List<String> remainImagesID) throws IOException, ServletException {

        Long productId = cryptoService.decryptEntityId(id);

        ProductUpdateRequest request = new ProductUpdateRequest();
        request.setCode(code);
        request.setName(name);
        request.setDescription(description);
        request.setQuantity(quantity);
        request.setExpireDate(expireDate != null ? LocalDate.parse(expireDate) : null);
        request.setBatchNumber(batchNumber);

        if (surveyId != null) {
            Boolean isValidSurvey = productService.validateSurvey(surveyId);
            if (!isValidSurvey) {
                throw new ComplexValidationException("surveyId", "productsUpdateRequest.surveyId.invalid");
            }
            request.setSurveyId(surveyId);
        }

        if (!files.isEmpty()) {
            //In first phase we are limiting image count as 4
            List< ProductUpdateRequest.ProductImageUpdateRequest> proImages = new ArrayList<>(4);
            for (MultipartFile file : files) {
                ProductUpdateRequest.ProductImageUpdateRequest productImageUpdateRequest = new ProductUpdateRequest.ProductImageUpdateRequest();
                productImageUpdateRequest.setImageName(file.getOriginalFilename());
                productImageUpdateRequest.setImageObject(file.getBytes());
                proImages.add(productImageUpdateRequest);
            }
            request.setImageObjects(proImages);
        }

        if(remainImagesID != null && !remainImagesID.isEmpty()){
            request.setRemainImagesID(remainImagesID);
        }

        ProductUpdateRequest.ClientData clientData = new ProductUpdateRequest.ClientData();
        Client dbClient = clientService.retrieve(cryptoService.decryptEntityId(client));
        if (dbClient == null) {
            throw new ComplexValidationException("client", "productsCreateRequest.client.invalid");
        }
        clientData.setId(client);
        request.setClient(clientData);

        Product product = modelMapper.map(request, Product.class);

        product.setId(productId);

        Product updatedClient = productService.update(product);

        ProductUpdateResponse response = modelMapper.map(updatedClient, ProductUpdateResponse.class);

        return new ResponseEntity<SimpleResponseWrapper<ProductUpdateResponse>>(new SimpleResponseWrapper<ProductUpdateResponse>(response), HttpStatus.OK);


    }

    @DeleteMapping("${app.endpoint.productsDelete}")
    public ResponseEntity<SimpleResponseWrapper<ProductDeleteResponse>> delete(@PathVariable String id) {

        Long productId = cryptoService.decryptEntityId(id);

        Product client = productService.delete(productId);

        ProductDeleteResponse response = modelMapper.map(client.getId(), ProductDeleteResponse.class);

        return new ResponseEntity<SimpleResponseWrapper<ProductDeleteResponse>>(new SimpleResponseWrapper<ProductDeleteResponse>(response), HttpStatus.OK);

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

    @GetMapping("/downloadFile/{objectId}")
    public ResponseEntity<InputStreamResource> downloadFile(@PathVariable String objectId) throws IOException, ServletException {
        Long productImageId = cryptoService.decryptEntityId(objectId);

        ProductImage retrievedImage = productService.retrieveProductImage(productImageId);

        byte[] imageByte = retrievedImage.getImageObject();
        String fileName = retrievedImage.getImageName();

        System.out.println("................Image Name..............: " + fileName);
        MediaType mediaType = MediaTypeUtils.getMediaTypeForFileName(this.servletContext, fileName);

        File imageFile = new File(fileName);
        OutputStream outPutStream = new FileOutputStream(imageFile);

        outPutStream.write(imageByte);

        InputStreamResource resource = new InputStreamResource(new FileInputStream(imageFile));

        return ResponseEntity.ok()
                // Content-Disposition
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + imageFile.getName())
                // Content-Type
                .contentType(mediaType)
                // Contet-Length
                .contentLength(imageFile.length())
                .body(resource);
    }


}
