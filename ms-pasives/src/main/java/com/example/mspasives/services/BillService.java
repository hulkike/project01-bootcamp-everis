package com.example.mspasives.services;

import com.example.mspasives.models.entities.Bill;
import com.example.mspasives.repositories.BillRepository;
import com.example.mspasives.repositories.IRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


@Service
public class BillService extends BaseService<Bill, String> implements IBillService {

    private final BillRepository repository;

    @Autowired
    public BillService(BillRepository repository) {
        this.repository = repository;
    }

    @Override
    protected IRepository<Bill, String> getRepository() {
        return repository;
    }

    @Override
    public Mono<Bill> findByAccountNumber(String accountNumber) {
        return repository.findByAccountNumber(accountNumber);
    }

}
