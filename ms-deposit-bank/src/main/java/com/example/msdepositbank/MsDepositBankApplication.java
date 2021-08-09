package com.example.msdepositbank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class MsDepositBankApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsDepositBankApplication.class, args);
	}

}
