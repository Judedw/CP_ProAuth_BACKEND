package com.clearpicture.platform.product.controller;

import com.clearpicture.platform.modelmapper.ModelMapper;
import com.clearpicture.platform.product.dto.request.ClientCreateRequest;
import com.clearpicture.platform.product.dto.request.ClientSearchRequest;
import com.clearpicture.platform.product.dto.request.ClientUpdateRequest;
import com.clearpicture.platform.product.dto.response.ClientCreateResponse;
import com.clearpicture.platform.product.dto.response.ClientDeleteResponse;
import com.clearpicture.platform.product.dto.response.ClientSearchResponse;
import com.clearpicture.platform.product.dto.response.ClientSuggestionResponse;
import com.clearpicture.platform.product.dto.response.ClientUpdateResponse;
import com.clearpicture.platform.product.dto.response.ClientViewResponse;
import com.clearpicture.platform.product.dto.response.external.ClientValidateResponse;
import com.clearpicture.platform.product.entity.Client;
import com.clearpicture.platform.product.entity.criteria.ClientSearchCriteria;
import com.clearpicture.platform.product.service.ClientService;
import com.clearpicture.platform.response.wrapper.ListResponseWrapper;
import com.clearpicture.platform.response.wrapper.PagingListResponseWrapper;
import com.clearpicture.platform.response.wrapper.SimpleResponseWrapper;
import com.clearpicture.platform.service.CryptoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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

import java.util.List;

/**
 * ClientController
 * Created by nuwan on 7/21/18.
 */
@Slf4j
@RestController
public class ClientController {

    @Autowired
    private ClientService clientService;

    @Autowired
    @Qualifier("modelMapper")
    private ModelMapper modelMapper;

    @Autowired
    private CryptoService cryptoService;

    @PostMapping("${app.endpoint.clientsCreate}")
    public ResponseEntity<SimpleResponseWrapper<ClientCreateResponse>> create(@Validated @RequestBody ClientCreateRequest request) {
        Client newClient = modelMapper.map(request,Client.class);
        Client savedClient = clientService.save(newClient);
        ClientCreateResponse response = modelMapper.map(savedClient,ClientCreateResponse.class);
        return new ResponseEntity<SimpleResponseWrapper<ClientCreateResponse>>(new SimpleResponseWrapper<>(response), HttpStatus.CREATED);
    }

    @GetMapping("${app.endpoint.clientsSearch}")
    public ResponseEntity<PagingListResponseWrapper<ClientSearchResponse>> retrieve(@Validated ClientSearchRequest request) {

        ClientSearchCriteria criteria = modelMapper.map(request,ClientSearchCriteria.class);

        Page<Client> results = clientService.search(criteria);

        List<ClientSearchResponse> clients =  modelMapper.map(results.getContent(),ClientSearchResponse.class);

        PagingListResponseWrapper.Pagination pagination = new PagingListResponseWrapper.Pagination(
                results.getNumber() + 1, results.getSize(), results.getTotalPages(), results.getTotalElements());

        return new ResponseEntity<PagingListResponseWrapper<ClientSearchResponse>>(new PagingListResponseWrapper<ClientSearchResponse>(clients,pagination),HttpStatus.OK);
    }

    @GetMapping("${app.endpoint.clientsView}")
    public ResponseEntity<SimpleResponseWrapper<ClientViewResponse>> retrieve(@PathVariable String id) {

        Long clientId = cryptoService.decryptEntityId(id);

        Client retrievedClient = clientService.retrieve(clientId);

        ClientViewResponse response = modelMapper.map(retrievedClient,ClientViewResponse.class);

        return new ResponseEntity<SimpleResponseWrapper<ClientViewResponse>>(new SimpleResponseWrapper<ClientViewResponse>(response),HttpStatus.OK);

    }

    @PutMapping("${app.endpoint.clientsUpdate}")
    public ResponseEntity<SimpleResponseWrapper<ClientUpdateResponse>> update(@PathVariable String id,
                                                                              @Validated @RequestBody ClientUpdateRequest request) {

        Long clientId = cryptoService.decryptEntityId(id);

        Client client = modelMapper.map(request,Client.class);

        client.setId(clientId);

        Client updatedClient = clientService.update(client);

        ClientUpdateResponse response = modelMapper.map(updatedClient,ClientUpdateResponse.class);

        return new ResponseEntity<SimpleResponseWrapper<ClientUpdateResponse>>(new SimpleResponseWrapper<ClientUpdateResponse>(response),HttpStatus.OK);


    }

    @DeleteMapping("${app.endpoint.clientsDelete}")
    public ResponseEntity<SimpleResponseWrapper<ClientDeleteResponse>> delete(@PathVariable String id) {

        Long clientId = cryptoService.decryptEntityId(id);

        Client client = clientService.delete(clientId);

        ClientDeleteResponse response = modelMapper.map(client.getId(),ClientDeleteResponse.class);

        return new ResponseEntity<SimpleResponseWrapper<ClientDeleteResponse>>(new SimpleResponseWrapper<ClientDeleteResponse>(response),HttpStatus.OK);
    }



    @GetMapping("${app.endpoint.clientsSuggestion}")
    public ResponseEntity<ListResponseWrapper<ClientSuggestionResponse>> retrieve() {
        List<Client> clients = clientService.retrieveForSuggestions();

        List<ClientSuggestionResponse> clientSuggestions = modelMapper.map(clients, ClientSuggestionResponse.class);

        return new ResponseEntity<ListResponseWrapper<ClientSuggestionResponse>>(
                new ListResponseWrapper<ClientSuggestionResponse>(clientSuggestions), HttpStatus.OK);
    }

    @GetMapping("${app.endpoint.clientsValidate}")
    public ResponseEntity<SimpleResponseWrapper<ClientValidateResponse>> validateClient(@RequestParam String clientId) {

        Long client = cryptoService.decryptEntityId(clientId);

        Client retrievedClient = clientService.retrieve(client);

        log.info("retrievedSurvey {}",retrievedClient);

        ClientValidateResponse response = new ClientValidateResponse();

        if(retrievedClient != null) {
            log.info("retrievedSurvey id {}",retrievedClient.getId());
            response.setIsValid(Boolean.TRUE);
        } else {
            response.setIsValid(Boolean.FALSE);
        }
        return new ResponseEntity<SimpleResponseWrapper<ClientValidateResponse>>(new SimpleResponseWrapper<ClientValidateResponse>(response),HttpStatus.OK);

    }
}
