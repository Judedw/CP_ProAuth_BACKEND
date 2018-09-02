package com.clearpicture.platform.survey.controller;

import com.clearpicture.platform.modelmapper.ModelMapper;
import com.clearpicture.platform.response.wrapper.PagingListResponseWrapper;
import com.clearpicture.platform.response.wrapper.SimpleResponseWrapper;
import com.clearpicture.platform.service.CryptoService;
import com.clearpicture.platform.survey.dto.request.EVoteCreateRequest;
import com.clearpicture.platform.survey.dto.request.EVoteSearchRequest;
import com.clearpicture.platform.survey.dto.response.EVoteCreateResponse;
import com.clearpicture.platform.survey.dto.response.EVoteSearchResponse;
import com.clearpicture.platform.survey.dto.response.EVoteViewResponse;
import com.clearpicture.platform.survey.entity.EVote;
import com.clearpicture.platform.survey.entity.criteria.EVoteSearchCriteria;
import com.clearpicture.platform.survey.service.EVoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

/**
 * EVoteController
 * Created by nuwan on 8/23/18.
 */
@RestController
public class EVoteController {

    @Autowired
    private EVoteService eVoteService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CryptoService cryptoService;

    @PostMapping(value = "${app.endpoint.evotesCreate}")
    public ResponseEntity<SimpleResponseWrapper<EVoteCreateResponse>> create(
            @RequestParam("file")MultipartFile file ,@RequestParam("code") String code,
            @RequestParam(value = "quantity")String quantity ,@RequestParam(value = "expireDate",required = false) String expireDate,
            @RequestParam(value = "topic",required = false)String topic ,@RequestParam(value = "description",required = false) String description,
            @RequestParam(value = "batchNumber",required = false)String batchNumber ,@RequestParam(value = "client") String client) {

        //String fileName = fileStorageService.storeFile(file);
        EVoteCreateRequest request = new EVoteCreateRequest();
        request.setCode(code);
        request.setTopic(topic);
        request.setDescription(description);
        request.setQuantity(quantity);
        request.setExpireDate(expireDate != null ? LocalDate.parse(expireDate) : null);
        request.setBatchNumber(batchNumber);
        //request.setImageName(fileName);
        request.setClientId(client);

        EVote eVote = modelMapper.map(request,EVote.class);
        EVote saveEVote = eVoteService.save(eVote);
        EVoteCreateResponse response = modelMapper.map(saveEVote,EVoteCreateResponse.class);

        return new ResponseEntity<SimpleResponseWrapper<EVoteCreateResponse>>(new SimpleResponseWrapper<>(response), HttpStatus.CREATED);
    }

    @GetMapping("${app.endpoint.evotesSearch}")
    public ResponseEntity<PagingListResponseWrapper<EVoteSearchResponse>> retrieve(@Validated EVoteSearchRequest request) {

        EVoteSearchCriteria criteria = modelMapper.map(request,EVoteSearchCriteria.class);

        Page<EVote> results = eVoteService.search(criteria);

        List<EVoteSearchResponse> products =  modelMapper.map(results.getContent(),EVoteSearchResponse.class);

        PagingListResponseWrapper.Pagination pagination = new PagingListResponseWrapper.Pagination(
                results.getNumber() + 1, results.getSize(), results.getTotalPages(), results.getTotalElements());

        return new ResponseEntity<PagingListResponseWrapper<EVoteSearchResponse>>(new PagingListResponseWrapper<EVoteSearchResponse>(products,pagination),HttpStatus.OK);
    }

    @GetMapping("${app.endpoint.evotesView}")
    public ResponseEntity<SimpleResponseWrapper<EVoteViewResponse>> view(@PathVariable String id) {

        Long productId = cryptoService.decryptEntityId(id);

        EVote retrievedProduct = eVoteService.retrieve(productId);

        EVoteViewResponse response = modelMapper.map(retrievedProduct,EVoteViewResponse.class);

        return new ResponseEntity<SimpleResponseWrapper<EVoteViewResponse>>(new SimpleResponseWrapper<EVoteViewResponse>(response),HttpStatus.OK);


    }

}
