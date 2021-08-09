package com.example.msacquisitionbank.config;

import com.example.msacquisitionbank.handler.AcquisitionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterConfig {
    @Bean
    public RouterFunction<ServerResponse> rutas(AcquisitionHandler handler){
        return route(GET("/acquisition"), handler::findAll)
                .andRoute(GET("/acquisition/{id}"), handler::findById)
                .andRoute(GET("/acquisition/product/{productName}"), handler::findByProductName)
                .andRoute(GET("/acquisition/productId/{productId}"), handler::findByProductId)
                .andRoute(GET("/acquisition/customer/{identityNumber}"), handler::findByIdentityNumber)
                .andRoute(POST("/acquisition/create"), handler::createAcquisitionTest2);
    }
}