package com.example.msretire.handler;

import com.example.msretire.models.entities.Bill;
import com.example.msretire.models.entities.Retire;
import com.example.msretire.models.entities.Transaction;
import com.example.msretire.services.BillService;
import com.example.msretire.services.IRetireService;
import com.example.msretire.services.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Component
@Slf4j(topic = "RETIRE_HANDLER")
public class RetireHandler {
    private final IRetireService retireService;
    private final BillService billService;
    private final TransactionService transactionService;

    @Autowired
    public RetireHandler(IRetireService retireService, BillService billService, TransactionService transactionService) {
        this.retireService = retireService;
        this.billService = billService;
        this.transactionService = transactionService;
    }

    public Mono<ServerResponse> findAll(ServerRequest request){
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(retireService.findAll(), Retire.class);
    }

    public Mono<ServerResponse> findById(ServerRequest request){
        String id = request.pathVariable("id");
        return errorHandler(
                retireService.findById(id).flatMap(p -> ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(p))
                        .switchIfEmpty(ServerResponse.notFound().build())
        );
    }

    public Mono<ServerResponse> findByAccountNumber(ServerRequest request){
        String accountNumber = request.pathVariable("accountNumber");
        log.info("ACCOUNT_NUMBER_WEBCLIENT {}", accountNumber);
        return billService.findByAccountNumber(accountNumber).flatMap(p -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(p))
                .switchIfEmpty(Mono.error(new RuntimeException("THE ACCOUNT NUMBER DOES NOT EXIST")));
    }

    public Mono<ServerResponse> updateBill(ServerRequest request){
        Mono<Bill> bill = request.bodyToMono(Bill.class);
        return bill.flatMap(billUpdate -> {
                Mono<Bill> billUpdated = billService.updateBill(billUpdate);
                return billUpdated;
                })
                .flatMap(billUpdate -> ServerResponse.created(URI.create("/bill/".concat(billUpdate.getAccountNumber())))
                        .contentType(APPLICATION_JSON)
                        .bodyValue(billUpdate))
                .onErrorResume(e -> Mono.error(new RuntimeException("Error update bill")));
    }

    public Mono<ServerResponse> createTransaction(ServerRequest request){
        Mono<Transaction> transaction = request.bodyToMono(Transaction.class);
        return transaction.flatMap(transactionCreate -> {
            Mono<Transaction> transactionCreated = transactionService.createTransaction(transactionCreate);
            log.info("TRANSACTION_RETIRE {}", transactionCreated);
            return transactionCreated;
        }).flatMap(transactionResponse -> ServerResponse.created(URI.create("/transaction/".concat(transactionResponse.getBill().getAccountNumber())))
                        .contentType(APPLICATION_JSON)
                        .bodyValue(transactionResponse))
                .onErrorResume(e -> Mono.error(new RuntimeException("Error create transaction")));
    }

    public Mono<ServerResponse> createRetire(ServerRequest request){
        Mono<Retire> retire = request.bodyToMono(Retire.class);
        return retire.flatMap(retireRequest ->  billService.findByAccountNumber(retireRequest.getBill().getAccountNumber())
                        .flatMap(billR -> {
                            billR.setBalance(billR.getBalance() - retireRequest.getAmount());
                            /*if (retireRequest.getAmount() > billR.getBalance()){
                                return Mono.error(new RuntimeException("The retire amount exceeds the available balance"));
                            }*/
                            return billService.updateBill(billR);
                        })
                        .flatMap(bilTransaction -> {
                            Transaction transaction = new Transaction();
                            transaction.setTransactionType("RETIRE");
                            transaction.setTransactionAmount(retireRequest.getAmount());
                            transaction.setBill(bilTransaction);
                            transaction.setDescription(retireRequest.getDescription());
                            return transactionService.createTransaction(transaction);
                        })
                        .flatMap(currentTransaction -> {
                            retireRequest.setBill(currentTransaction.getBill());
                            return retireService.create(retireRequest);
                        })).flatMap(retireUpdate -> ServerResponse.created(URI.create("/retire/".concat(retireUpdate.getId())))
                        .contentType(APPLICATION_JSON)
                        .bodyValue(retireUpdate))
                .onErrorResume(e -> Mono.error(new RuntimeException("Error update retire")));
    }

    public Mono<ServerResponse> save(ServerRequest request){
        Mono<Retire> product = request.bodyToMono(Retire.class);
        return product.flatMap(retireService::create)
                .flatMap(p -> ServerResponse.created(URI.create("/retire/".concat(p.getId())))
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(p))
                .onErrorResume(error -> {
                    WebClientResponseException errorResponse = (WebClientResponseException) error;
                    if(errorResponse.getStatusCode() == HttpStatus.BAD_REQUEST) {
                        return ServerResponse.badRequest()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(errorResponse.getResponseBodyAsString());
                    }
                    return Mono.error(errorResponse);
                });
    }

    private Mono<ServerResponse> errorHandler(Mono<ServerResponse> response){
        return response.onErrorResume(error -> {
            WebClientResponseException errorResponse = (WebClientResponseException) error;
            if(errorResponse.getStatusCode() == HttpStatus.NOT_FOUND) {
                Map<String, Object> body = new HashMap<>();
                body.put("error", "the retire does not exist: ".concat(errorResponse.getMessage()));
                body.put("timestamp", new Date());
                body.put("status", errorResponse.getStatusCode().value());
                return ServerResponse.status(HttpStatus.NOT_FOUND).bodyValue(body);
            }
            return Mono.error(errorResponse);
        });
    }
}
