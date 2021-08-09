package com.example.msretire.config;

import com.example.msretire.handler.RetireHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterConfig {
    @Bean
    public RouterFunction<ServerResponse> rutas(RetireHandler handler){
        return route(GET("/retire"), handler::findAll)
                .andRoute(GET("/retire/{id}"), handler::findById)
                .andRoute(GET("/retire/acc/{accountNumber}"), handler::findByAccountNumber)
                .andRoute(POST("/retire"), handler::save)
                .andRoute(POST("/retire/create"), handler::createRetire)
                .andRoute(POST("/retire/update/bill"), handler::updateBill)
                .andRoute(POST("/retire/create/transaction"), handler::createTransaction);
    }
}