package com.clearpicture.platform.survey;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

import java.security.Security;

@EnableResourceServer
@SpringBootApplication(scanBasePackages = {"com.clearpicture.platform"},exclude = {org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration.class})
public class SurveyServiceApplication {

	public static void main(String[] args) {
        Security.setProperty("crypto.policy", "unlimited");
		SpringApplication.run(SurveyServiceApplication.class, args);
	}
}
