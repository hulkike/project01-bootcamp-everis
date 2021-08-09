package com.bootcamp.msCredit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class MsCreditBankApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsCreditBankApplication.class, args);
	}

}
