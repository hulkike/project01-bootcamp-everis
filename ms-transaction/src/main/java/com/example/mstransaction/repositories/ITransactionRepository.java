package com.example.mstransaction.repositories;

import com.example.mstransaction.models.entities.Transaction;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ITransactionRepository extends IRepository<Transaction, String>{
    Mono<List<Transaction>> findAllByBill_AccountNumber(String accountNumber);
}
