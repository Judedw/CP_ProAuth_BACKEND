package com.clearpicture.platform.product.service.impl;

import com.clearpicture.platform.exception.ComplexValidationException;
import com.clearpicture.platform.product.entity.Client;
import com.clearpicture.platform.product.entity.Product;
import com.clearpicture.platform.product.entity.QClient;
import com.clearpicture.platform.product.entity.criteria.ClientSearchCriteria;
import com.clearpicture.platform.product.repository.ClientRepository;
import com.clearpicture.platform.product.service.ClientService;
import com.querydsl.core.BooleanBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

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
        Client dbClient = clientRepository.findByCode(client.getCode());
        if(dbClient == null) {
            return clientRepository.save(client);
        } else {
            throw new ComplexValidationException("code","clientCreateRequest.duplicate.code");
        }
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
        try {
            Optional<Client> client = clientRepository.findById(id);
            if(client != null) {
                return client.get();
            } else {
                throw new ComplexValidationException("client", "clientViewRequest.clientNotExist");
            }
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Client update(Client client) {
        Client currentClient = null;
        try {
            currentClient = clientRepository.getOne(client.getId());
        } catch (EntityNotFoundException e) {
            throw new ComplexValidationException("client", "clientUpdateRequest.clientNotExist");
        }
        currentClient.setName(client.getName());
        currentClient.setCode(client.getCode());
        currentClient.setDescription(client.getDescription());
        return clientRepository.save(client);
    }

    @Override
    public Client delete(Long id) {
        Client currentClient = null;
        try {
            currentClient = clientRepository.getOne(id);
        } catch (EntityNotFoundException e) {
            throw new ComplexValidationException("client", "clientDeleteRequest.clientNotExist");
        }
        clientRepository.delete(currentClient);
        return currentClient;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Client> retrieveForSuggestions() {
        return clientRepository.findAll();
    }
}
