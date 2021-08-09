package com.example.mspasives.handler;
import static org.springframework.http.MediaType.*;

import com.example.mspasives.models.entities.Bill;
import com.example.mspasives.models.entities.Transaction;
import com.example.mspasives.services.BillService;
import com.example.mspasives.services.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j(topic = "BILL_HANDLER")
public class BillHandler {

    @Autowired
    private BillService billService;
    @Autowired
    private TransactionService transactionService;

    public Mono<ServerResponse> findAll(ServerRequest request){
        return ServerResponse.ok().contentType(APPLICATION_JSON)
                .body(billService.findAll(), Bill.class);
    }

    public Mono<ServerResponse> findById(ServerRequest request){
        String id = request.pathVariable("id");
        return errorHandler(
                billService.findById(id).flatMap(p -> ServerResponse.ok()
                                .contentType(APPLICATION_JSON)
                                .bodyValue(p))
                        .switchIfEmpty(ServerResponse.notFound().build())
        );
    }

    public Mono<ServerResponse> findByAccountNumber(ServerRequest request){
        String accountNumber = request.pathVariable("accountNumber");
        return errorHandler(
                billService.findByAccountNumber(accountNumber).flatMap(p -> ServerResponse.ok()
                                .contentType(APPLICATION_JSON)
                                .bodyValue(p))
                        .switchIfEmpty(ServerResponse.notFound().build())
        );
    }

    public Mono<ServerResponse> save(ServerRequest request){
        Mono<Bill> bill = request.bodyToMono(Bill.class);
        return bill.flatMap(p-> {
                    return billService.create(p);
                }).flatMap(p -> ServerResponse.created(URI.create("/bill/".concat(p.getId())))
                        .contentType(APPLICATION_JSON)
                        .bodyValue(p))
                .onErrorResume(error -> {
                    WebClientResponseException errorResponse = (WebClientResponseException) error;
                    if(errorResponse.getStatusCode() == HttpStatus.BAD_REQUEST) {
                        return ServerResponse.badRequest()
                                .contentType(APPLICATION_JSON)
                                .bodyValue(errorResponse.getResponseBodyAsString());
                    }
                    return Mono.error(errorResponse);
                });
    }

    public Mono<ServerResponse> update(ServerRequest request){
        Mono<Bill> bill = request.bodyToMono(Bill.class);
        log.info("BILL_FROM_RETIRE {}", bill);
        return bill.flatMap(billEdit -> billService.findByAccountNumber(billEdit.getAccountNumber())
                        .flatMap(currentBill -> {
                            currentBill.setAccountNumber(billEdit.getAccountNumber());
                            currentBill.setBalance(billEdit.getBalance());
                            currentBill.setDateOpened(null);
                            currentBill.setLimitMovementsMonth(10);
                            return billService.update(currentBill);
                        })).flatMap(billUpdate -> ServerResponse.created(URI.create("/bill/".concat(billUpdate.getId())))
                        .contentType(APPLICATION_JSON)
                        .bodyValue(billUpdate))
                .onErrorResume(e -> Mono.error(new RuntimeException("Error update bill")));
    }

    private Mono<ServerResponse> errorHandler(Mono<ServerResponse> response){
        return response.onErrorResume(error -> {
            WebClientResponseException errorResponse = (WebClientResponseException) error;
            if(errorResponse.getStatusCode() == HttpStatus.NOT_FOUND) {
                Map<String, Object> body = new HashMap<>();
                body.put("error", "No existe el producto: ".concat(errorResponse.getMessage()));
                body.put("timestamp", new Date());
                body.put("status", errorResponse.getStatusCode().value());
                return ServerResponse.status(HttpStatus.NOT_FOUND).syncBody(body);
            }
            return Mono.error(errorResponse);
        });
    }
}
