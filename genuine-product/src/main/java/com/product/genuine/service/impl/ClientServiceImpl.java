package com.product.genuine.service.impl;

import com.product.genuine.entity.Client;
import com.product.genuine.entity.QClient;
import com.product.genuine.entity.criteria.ClientSearchCriteria;
import com.product.genuine.repository.ClientRepository;
import com.product.genuine.service.ClientService;
import com.querydsl.core.BooleanBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * ClientServiceImpl
 * Created by nuwan on 7/21/18.
 */
@Service
@Transactional
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public Client save(Client client) {
        return clientRepository.save(client);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Client> search(ClientSearchCriteria criteria) {
        PageRequest page = PageRequest.of(criteria.getPageNumber() - 1, criteria.getPageSize(),
                Sort.Direction.fromString(criteria.getSortDirection() == null ? null : criteria.getSortDirection()), criteria.getSortProperty());
        Page<Client> clients = null;

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        if(StringUtils.isNotBlank(criteria.getName())) {
            booleanBuilder.and(QClient.client.name.containsIgnoreCase(criteria.getName()));

        }

        if (booleanBuilder.hasValue()) {
            clients = clientRepository.findAll(booleanBuilder, page);
        } else {
            clients = clientRepository.findAll(page);
        }
        return clients;
    }

    @Transactional(readOnly = true)
    @Override
    public Client retrieve(Long id) {
        Client client = clientRepository.getOne(id);
        return client;
    }

    @Override
    public Client update(Client client) {
        Client currentClient = clientRepository.getOne(client.getId());

        if(currentClient == null) {

        }
        currentClient.setName(client.getName());
        currentClient.setCode(client.getCode());
        currentClient.setDescription(client.getDescription());
        return clientRepository.save(client);
    }

    @Override
    public Client delete(Long id) {
        Client currentClient = clientRepository.getOne(id);

        if(currentClient == null) {

        }

        //if client attached to product to will not permit to delete client

        clientRepository.delete(currentClient);

        return currentClient;


    }

    @Transactional(readOnly = true)
    @Override
    public List<Client> retrieveForSuggestions() {

        return clientRepository.findAll();
    }
}
