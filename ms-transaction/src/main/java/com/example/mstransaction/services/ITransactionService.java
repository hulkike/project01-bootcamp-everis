package com.example.mstransaction.services;

import com.example.mstransaction.models.entities.Transaction;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ITransactionService extends IBaseService<Transaction, String>{
    Mono<List<Transaction>> findAllByBill_AccountNumber(String accountNumber);
}
