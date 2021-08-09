package com.example.mspasives.services;

import com.example.mspasives.models.entities.BillType;
import com.example.mspasives.repositories.BillTypeRepository;
import com.example.mspasives.repositories.IRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BillTypeService extends BaseService<BillType, String> implements IBillTypeService{
    @Autowired
    private BillTypeRepository billTypeRepository;

    @Override
    protected IRepository<BillType, String> getRepository() {
        return billTypeRepository;
    }
}
