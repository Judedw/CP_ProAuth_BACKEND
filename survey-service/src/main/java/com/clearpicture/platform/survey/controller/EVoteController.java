package com.clearpicture.platform.survey.controller;


import com.clearpicture.platform.dto.request.GeneralSuggestionRequest;
import com.clearpicture.platform.exception.ComplexValidationException;
import com.clearpicture.platform.modelmapper.ModelMapper;
import com.clearpicture.platform.response.wrapper.ListResponseWrapper;
import com.clearpicture.platform.response.wrapper.PagingListResponseWrapper;
import com.clearpicture.platform.response.wrapper.SimpleResponseWrapper;
import com.clearpicture.platform.service.CryptoService;
import com.clearpicture.platform.survey.dto.request.EVoteCreateRequest;
import com.clearpicture.platform.survey.dto.request.EVoteSearchRequest;
import com.clearpicture.platform.survey.dto.request.EVoteUpdateRequest;
import com.clearpicture.platform.survey.dto.response.*;
import com.clearpicture.platform.survey.entity.EVote;
import com.clearpicture.platform.survey.entity.Survey;
import com.clearpicture.platform.survey.entity.criteria.EVoteSearchCriteria;
import com.clearpicture.platform.survey.service.EVoteService;
import com.clearpicture.platform.survey.service.FileStorageService;
import com.clearpicture.platform.survey.service.Ms2msCommunicationService;
import com.clearpicture.platform.survey.service.SurveyService;
import com.clearpicture.platform.survey.util.MediaTypeUtils;
import com.clearpicture.platform.survey.validation.validator.EVoteUpdateRequestValidation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.io.*;
import java.time.LocalDate;
import java.util.List;

/**
 * EVoteController
 * Created by nuwan on 8/23/18.
 */
@Slf4j
@RestController
public class EVoteController {

    @Autowired
    private EVoteService eVoteService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CryptoService cryptoService;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private SurveyService surveyService;

    @Autowired
    private ServletContext servletContext;

    @Autowired
    private Ms2msCommunicationService ms2msCommunicationService;

    @PostMapping(value = "${app.endpoint.evotesCreate}")
    public ResponseEntity<SimpleResponseWrapper<EVoteCreateResponse>> create(
            @RequestParam(value = "file", required = false) MultipartFile file, @RequestParam(value = "code", required = false) String code,
            @RequestParam(value = "quantity", required = false) String quantity, @RequestParam(value = "expireDate", required = false) String expireDate,
            @RequestParam(value = "topic", required = false) String topic, @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "batchNumber", required = false) String batchNumber, @RequestParam(value = "clientId", required = false) String clientId,
            @RequestParam(value = "surveyId", required = false) String surveyId) {

        EVoteCreateRequest request = new EVoteCreateRequest();
        request.setCode(code);
        request.setTopic(topic);
        request.setDescription(description);
        request.setQuantity(quantity);
        request.setExpireDate(expireDate != null ? LocalDate.parse(expireDate) : null);
        request.setBatchNumber(batchNumber);
        request.setClientId(clientId);
        System.out.println("clientId : " + clientId);
        System.out.println("topic : " + topic);
        if (surveyId != null && !surveyId.isEmpty())
            request.setSurveyId(surveyId);
        if (file != null) {
            request.setImageName(file.getOriginalFilename());
            try {
                // request.setImageObject(fileStorageService.storeFile(file));
                request.setImageObject(file.getBytes());

            } catch (IOException e) {
                throw new ComplexValidationException("eVote", "eVoteCreateRequest.canNotStoreFile");
            }
//            catch (ServletException e) {
//                throw  new ComplexValidationException("eVote","eVoteCreateRequest.canNotStoreFile");
//            }
        }

        validate(request);


        EVote eVote = modelMapper.map(request, EVote.class);
        /*eVote.setClientId(cryptoService.decryptEntityId(client));
        if(survey != null)
            eVote.setSurveyId(cryptoService.decryptEntityId(survey));*/
        try {
            EVote saveEVote = eVoteService.save(eVote);
            EVoteCreateResponse response = modelMapper.map(saveEVote, EVoteCreateResponse.class);
            return new ResponseEntity<SimpleResponseWrapper<EVoteCreateResponse>>(new SimpleResponseWrapper<>(response), HttpStatus.CREATED);
        } catch (Exception e) {
            throw new ComplexValidationException("eVote", e.toString());
        }


        /*response.setClientId(cryptoService.encryptEntityId(saveEVote.getClientId()));
        if(saveEVote.getSurveyId()!= null)
            response.setSurveyId(cryptoService.encryptEntityId(saveEVote.getSurveyId()));*/


    }

    @GetMapping("${app.endpoint.evotesSearch}")
    public ResponseEntity<PagingListResponseWrapper<EVoteSearchResponse>> retrieve(@Validated EVoteSearchRequest request) {

        EVoteSearchCriteria criteria = modelMapper.map(request, EVoteSearchCriteria.class);

        Page<EVote> results = eVoteService.search(criteria);

        List<EVoteSearchResponse> products = modelMapper.map(results.getContent(), EVoteSearchResponse.class);

        PagingListResponseWrapper.Pagination pagination = new PagingListResponseWrapper.Pagination(
                results.getNumber() + 1, results.getSize(), results.getTotalPages(), results.getTotalElements());

        return new ResponseEntity<PagingListResponseWrapper<EVoteSearchResponse>>(new PagingListResponseWrapper<EVoteSearchResponse>(products, pagination), HttpStatus.OK);
    }

    @GetMapping("${app.endpoint.evotesView}")
    public ResponseEntity<SimpleResponseWrapper<EVoteViewResponse>> view(@PathVariable String id) {

        Long productId = cryptoService.decryptEntityId(id);

        EVote retrievedProduct = eVoteService.retrieve(productId);

        EVoteViewResponse response = modelMapper.map(retrievedProduct, EVoteViewResponse.class);

        return new ResponseEntity<SimpleResponseWrapper<EVoteViewResponse>>(new SimpleResponseWrapper<EVoteViewResponse>(response), HttpStatus.OK);


    }

    @PutMapping("${app.endpoint.evotesUpdate}")
    public ResponseEntity<SimpleResponseWrapper<EVoteUpdateResponse>> update(@PathVariable String id, @RequestParam(value = "file", required = false) MultipartFile file, @RequestParam(value = "code", required = false) String code,
                                                                             @RequestParam(value = "quantity", required = false) String quantity, @RequestParam(value = "expireDate", required = false) String expireDate,
                                                                             @RequestParam(value = "topic", required = false) String topic, @RequestParam(value = "description", required = false) String description,
                                                                             @RequestParam(value = "batchNumber", required = false) String batchNumber, @RequestParam(value = "clientId", required = false) String clientId,
                                                                             @RequestParam(value = "surveyId", required = false) String surveyId) {
        Long eVoteId = cryptoService.decryptEntityId(id);

        EVoteUpdateRequest request = new EVoteUpdateRequest();
        request.setCode(code);
        request.setTopic(topic);
        request.setDescription(description);
        request.setQuantity(quantity);
        request.setExpireDate(expireDate != null ? LocalDate.parse(expireDate) : null);
        request.setBatchNumber(batchNumber);
        request.setClientId(clientId);
        request.setSurveyId(surveyId);
        if (file != null) {
            request.setImageName(file.getOriginalFilename());
            try {
                request.setImageObject(fileStorageService.storeFile(file));
            } catch (IOException e) {

                throw new ComplexValidationException("eVote", "eVoteUpdateRequest.canNotStoreFile");

            } catch (ServletException e) {
                throw new ComplexValidationException("eVote", "eVoteUpdateRequest.canNotStoreFile");
            }
        }

        EVoteUpdateRequestValidation validation = new EVoteUpdateRequestValidation();
        validation.validate(request);

        EVote eVote = modelMapper.map(request, EVote.class);

        eVote.setId(eVoteId);

        EVote updatedEVote = eVoteService.update(eVote);

        EVoteUpdateResponse response = modelMapper.map(updatedEVote, EVoteUpdateResponse.class);

        return new ResponseEntity<SimpleResponseWrapper<EVoteUpdateResponse>>(new SimpleResponseWrapper<EVoteUpdateResponse>(response), HttpStatus.OK);


    }

    @GetMapping("${app.endpoint.evotesSuggestion}")
    public ResponseEntity<ListResponseWrapper<EVoteSuggestionResponse>> retrieve(GeneralSuggestionRequest request) {
        List<EVote> eVotes = eVoteService.retrieveForSuggestions(request.getKeyword());

        List<EVoteSuggestionResponse> answerTemplatesSuggestions = modelMapper.map(eVotes, EVoteSuggestionResponse.class);

        return new ResponseEntity<ListResponseWrapper<EVoteSuggestionResponse>>(
                new ListResponseWrapper<EVoteSuggestionResponse>(answerTemplatesSuggestions), HttpStatus.OK);
    }

    @DeleteMapping("${app.endpoint.evotesDelete}")
    public ResponseEntity<SimpleResponseWrapper<EvoteDeleteResponse>> delete(@PathVariable String id) {

        Long evoteId = cryptoService.decryptEntityId(id);

        EVote evote = eVoteService.delete(evoteId);

        EvoteDeleteResponse response = modelMapper.map(evote.getId(), EvoteDeleteResponse.class);

        return new ResponseEntity<SimpleResponseWrapper<EvoteDeleteResponse>>(new SimpleResponseWrapper<EvoteDeleteResponse>(response), HttpStatus.OK);

    }

    @GetMapping("/downloadFile/{objectId}")
    public ResponseEntity<InputStreamResource> downloadFile(@PathVariable String objectId) throws IOException, ServletException {
        Long evoteId = cryptoService.decryptEntityId(objectId);

        EVote retrievedEvote = eVoteService.retrieve(evoteId);
        byte[] imageByte = retrievedEvote.getImageObject();
        String fileName = retrievedEvote.getImageName();

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


    public void validate(EVoteCreateRequest request) {

        if (request.getSurveyId() != null) {
            Survey survey = surveyService.retrieve(cryptoService.decryptEntityId(request.getSurveyId()));
            if (survey == null) {
                throw new ComplexValidationException("survey", "EVoteCreateRequest.survey.invalid");
            }
        }

        if (request.getClientId() == null) {
            throw new ComplexValidationException("client", "EVoteCreateRequest.client.empty");
        } else {
            if (!ms2msCommunicationService.validateClient(request.getClientId())) {
                throw new ComplexValidationException("client", "EVoteCreateRequest.client.invalid");
            }
        }

        if (request.getTopic() == null) {
            throw new ComplexValidationException("client", "EVoteCreateRequest.client.empty");
        }

    }

}
