package com.example.msretire.services;

import com.example.msretire.models.entities.Retire;
import com.example.msretire.repository.IRepository;
import com.example.msretire.repository.IRetireRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RetireService extends BaseService<Retire, String> implements IRetireService{

    private final IRetireRepository retireRepository;

    @Autowired
    public RetireService(IRetireRepository retireRepository) {
        this.retireRepository = retireRepository;
    }

    @Override
    protected IRepository<Retire, String> getRepository() {
        return retireRepository;
    }
}
