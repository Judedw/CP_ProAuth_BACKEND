package com.product.genuine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.product.genuine"},exclude = {org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration.class})
public class GspFrameworkApplication {

	public static void main(String[] args) {
		SpringApplication.run(GspFrameworkApplication.class, args);
	}
}
