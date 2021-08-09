package com.bootcamp.msCreditcard.config;

import com.bootcamp.msCreditcard.handler.CreditCardHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterConfig {

    @Bean
    public RouterFunction<ServerResponse> routes(CreditCardHandler creditCardHandler){

        return route(GET("/api/creditcard"), creditCardHandler::findAll)
                .andRoute(GET("/api/creditcard/payment/{pan}"), creditCardHandler::findCreditCardByPan)
                .andRoute(GET("/api/creditcard/{id}"), creditCardHandler::findCreditCard)
                .andRoute(POST("/api/creditcard/{customerIdentityNumber}"), creditCardHandler::newCreditCard)
                .andRoute(PUT("/api/creditcard/{id}"), creditCardHandler::updateCreditCard)
                .andRoute(DELETE("/api/creditcard/{id}"), creditCardHandler::deleteCreditCard);

    }
}
