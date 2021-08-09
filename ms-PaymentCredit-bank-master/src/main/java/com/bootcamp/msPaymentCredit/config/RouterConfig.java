package com.bootcamp.msPaymentCredit.config;

import com.bootcamp.msPaymentCredit.handler.PaymentCardHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterConfig {

    @Bean
    public RouterFunction<ServerResponse> routes(PaymentCardHandler paymentCardHandler){

        return route(GET("/api/paymentcard"), paymentCardHandler::findAll)
                .andRoute(GET("/api/paymentcard/{id}"), paymentCardHandler::findPaymentCard)
                .andRoute(POST("/api/paymentcard"), paymentCardHandler::newPaymentCard)
                .andRoute(PUT("/api/paymentcard/{id}"), paymentCardHandler::updatePaymentCard)
                .andRoute(DELETE("/api/paymentcard/{id}"), paymentCardHandler::deletePaymentCard);

    }
}
