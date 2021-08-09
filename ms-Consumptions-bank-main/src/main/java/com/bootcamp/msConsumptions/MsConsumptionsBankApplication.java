package com.bootcamp.msConsumptions;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class MsConsumptionsBankApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsConsumptionsBankApplication.class, args);
	}

}
