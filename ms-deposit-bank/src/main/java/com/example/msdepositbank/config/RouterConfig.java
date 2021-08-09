package com.example.msdepositbank.config;

import com.example.msdepositbank.handler.DepositHandler;
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
    public RouterFunction<ServerResponse> rutas(DepositHandler handler) {
        return route(GET("/deposit"), handler::findAll)
                .andRoute(GET("/deposit/{id}"), handler::findById)
                .andRoute(GET("/deposit/acc/{accountNumber}"), handler::findByAccountNumber)
                .andRoute(POST("/deposit/create"), handler::createDeposit);
    }
}
