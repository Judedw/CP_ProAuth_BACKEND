package com.clearpicture.platform.product.service;

import com.clearpicture.platform.product.entity.Client;
import com.clearpicture.platform.product.entity.criteria.ClientSearchCriteria;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * ClientService
 * Created by nuwan on 7/21/18.
 */
public interface ClientService {

    Client save(Client client);

    Page<Client> search(ClientSearchCriteria criteria);

    Client retrieve(Long id);

    Client update(Client client);

    Client delete(Long aLong);

    List<Client> retrieveForSuggestions();
}
