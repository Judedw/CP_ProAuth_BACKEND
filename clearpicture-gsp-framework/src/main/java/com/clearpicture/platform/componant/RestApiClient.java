package com.clearpicture.platform.componant;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


/**
 * @author Virajith
 *
 */
@Component
public class RestApiClient {

	@Autowired
	private RestTemplateBuilder restTemplateBuilder;
	private RestTemplate restTemplate;

	public RestApiClient() {}

	@PostConstruct
	public void init()
	{
		restTemplate = restTemplateBuilder.build();
	}

	public RestTemplate getRestTemplate()
	{
		return restTemplate;
	}

}
