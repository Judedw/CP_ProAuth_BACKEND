package com.product.genuine.controller;

import com.product.genuine.dto.request.ClientCreateRequest;
import com.product.genuine.dto.request.ClientSearchRequest;
import com.product.genuine.dto.request.ClientUpdateRequest;
import com.product.genuine.dto.response.ClientCreateResponse;
import com.product.genuine.dto.response.ClientDeleteResponse;
import com.product.genuine.dto.response.ClientSearchResponse;
import com.product.genuine.dto.response.ClientSuggestionResponse;
import com.product.genuine.dto.response.ClientUpdateResponse;
import com.product.genuine.dto.response.ClientViewResponse;
import com.product.genuine.dto.response.wrapper.ListResponseWrapper;
import com.product.genuine.dto.response.wrapper.PagingListResponseWrapper;
import com.product.genuine.dto.response.wrapper.SimpleResponseWrapper;
import com.product.genuine.entity.Client;
import com.product.genuine.entity.criteria.ClientSearchCriteria;
import com.product.genuine.modelmapper.ModelMapper;
import com.product.genuine.service.ClientService;
import com.product.genuine.service.CryptoService;
import lombok.extern.slf4j.Slf4j;
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
 * ClientController
 * Created by nuwan on 7/21/18.
 */
@Slf4j
@RestController
public class ClientController {

    @Autowired
    private ClientService clientService;

    @Autowired
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
    public ResponseEntity<PagingListResponseWrapper <ClientSearchResponse>> retrieve(@Validated ClientSearchRequest request) {

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
}
