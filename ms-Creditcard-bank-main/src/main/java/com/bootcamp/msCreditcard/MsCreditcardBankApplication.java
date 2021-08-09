package com.bootcamp.msCreditcard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class MsCreditcardBankApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsCreditcardBankApplication.class, args);
	}

}
