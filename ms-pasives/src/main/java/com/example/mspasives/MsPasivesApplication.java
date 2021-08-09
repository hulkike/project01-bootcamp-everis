package com.example.mspasives;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class MsPasivesApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsPasivesApplication.class, args);
    }

}
