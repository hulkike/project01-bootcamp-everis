package com.example.mspasives.config;

import com.example.mspasives.handler.BillHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class RouterConfig {

    @Bean
    public RouterFunction<ServerResponse> rutas(BillHandler handler){
        return route(GET("/bill"), handler::findAll)
                .andRoute(GET("/bill/{id}"), handler::findById)
                .andRoute(GET("/bill/acc/{accountNumber}"), handler::findByAccountNumber)
                .andRoute(POST("/bill/save"), handler::save)
                .andRoute(POST("/bill/update"), handler::update);

    }

}