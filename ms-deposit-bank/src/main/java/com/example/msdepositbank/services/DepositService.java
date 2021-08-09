package com.example.msdepositbank.services;

import com.example.msdepositbank.models.entities.Deposit;
import com.example.msdepositbank.repository.IRepository;
import com.example.msdepositbank.repository.IRetireRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DepositService extends BaseService<Deposit, String> implements IDepositService{

    private final IRetireRepository depositRepository;

    @Autowired
    public DepositService(IRetireRepository depositRepository) {
        this.depositRepository = depositRepository;
    }

    @Override
    protected IRepository<Deposit, String> getRepository() {
        return depositRepository;
    }
}
