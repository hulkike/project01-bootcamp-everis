package com.bootcamp.msConsumptions.config;

import com.bootcamp.msConsumptions.handler.ConsumptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterConfig {

    @Bean
    public RouterFunction<ServerResponse> routes(ConsumptionHandler consumptionHandler){

        return route(GET("/api/consumption"), consumptionHandler::findAll)
                .andRoute(GET("/api/consumption/{id}"), consumptionHandler::findConsumption)
                .andRoute(POST("/api/consumption"), consumptionHandler::newConsumption)
                .andRoute(PUT("/api/consumption/{id}"), consumptionHandler::updateConsumption)
                .andRoute(DELETE("/api/consumption/{id}"), consumptionHandler::deleteConsumption);
    }
}
