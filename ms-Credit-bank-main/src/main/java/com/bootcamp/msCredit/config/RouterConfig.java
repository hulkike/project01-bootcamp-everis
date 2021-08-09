package com.bootcamp.msCredit.config;

import com.bootcamp.msCredit.handler.CreditHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterConfig {

    @Bean
    public RouterFunction<ServerResponse> routes(CreditHandler creditHandler){

        return route(GET("/api/credit"), creditHandler::findAll)
                .andRoute(GET("/api/creditsUser/{customerIdentityNumber}"),creditHandler::findAllByCustomerIdentityNumber)
                .andRoute(GET("/api/credit/{id}"), creditHandler::findCredit)
                .andRoute(POST("/api/credit"), creditHandler::newCredit)
                .andRoute(PUT("/api/credit/{id}"), creditHandler::updateCredit)
                .andRoute(DELETE("/api/credit/{id}"), creditHandler::deleteCredit);

    }

}
