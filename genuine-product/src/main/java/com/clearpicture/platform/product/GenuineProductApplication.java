package com.clearpicture.platform.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@EnableResourceServer
@SpringBootApplication(scanBasePackages = {"com.clearpicture.platform"},exclude = {org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration.class})
public class GenuineProductApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(GenuineProductApplication.class, args);
	}
}
