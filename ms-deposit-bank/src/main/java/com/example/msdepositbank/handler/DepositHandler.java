package com.example.msdepositbank.handler;

import com.example.msdepositbank.models.entities.Deposit;
import com.example.msdepositbank.models.entities.Transaction;
import com.example.msdepositbank.services.BillService;
import com.example.msdepositbank.services.IDepositService;
import com.example.msdepositbank.services.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Component
@Slf4j(topic = "DEPOSIT_HANDLER")
public class DepositHandler {
    private final IDepositService depositService;
    private final BillService billService;
    private final TransactionService transactionService;
    @Autowired
    public DepositHandler(IDepositService depositService, BillService billService, TransactionService transactionService) {
        this.depositService = depositService;
        this.billService = billService;
        this.transactionService = transactionService;
    }

    public Mono<ServerResponse> findAll(ServerRequest request){
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(depositService.findAll(), Deposit.class);
    }

    public Mono<ServerResponse> findById(ServerRequest request){
        String id = request.pathVariable("id");
        return depositService.findById(id).flatMap(deposit -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(deposit))
                .switchIfEmpty(Mono.error(new RuntimeException("DEPOSIT DOES NOT EXIST")));
    }

    public Mono<ServerResponse> findByAccountNumber(ServerRequest request){
        String accountNumber = request.pathVariable("accountNumber");
        log.info("ACCOUNT_NUMBER_WEBCLIENT {}", accountNumber);
        return billService.findByAccountNumber(accountNumber).flatMap(p -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(p))
                .switchIfEmpty(Mono.error(new RuntimeException("THE ACCOUNT NUMBER DOES NOT EXIST")));
    }

    public Mono<ServerResponse> createDeposit(ServerRequest request){
        Mono<Deposit> retire = request.bodyToMono(Deposit.class);
        return retire.flatMap(depositRequest ->  billService.findByAccountNumber(depositRequest.getBill().getAccountNumber())
                        .flatMap(billR -> {
                            billR.setBalance(billR.getBalance() + depositRequest.getAmount());
                            return billService.updateBill(billR);
                        })
                        .flatMap(bilTransaction -> {
                            Transaction transaction = new Transaction();
                            transaction.setTransactionType("DEPOSIT");
                            transaction.setTransactionAmount(depositRequest.getAmount());
                            transaction.setBill(bilTransaction);
                            transaction.setDescription(depositRequest.getDescription());
                            return transactionService.createTransaction(transaction);
                        })
                        .flatMap(currentTransaction -> {
                            depositRequest.setBill(currentTransaction.getBill());
                            return depositService.create(depositRequest);
                        })).flatMap(retireUpdate -> ServerResponse.created(URI.create("/retire/".concat(retireUpdate.getId())))
                        .contentType(APPLICATION_JSON)
                        .bodyValue(retireUpdate))
                .onErrorResume(e -> Mono.error(new RuntimeException("Error update deposit")));
    }
}
