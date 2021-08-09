package com.example.msacquisitionbank.services;

import com.example.msacquisitionbank.models.entities.Acquisition;
import com.example.msacquisitionbank.repositories.IAcquisitionRepository;
import com.example.msacquisitionbank.repositories.IRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AcquisitionService extends BaseService<Acquisition, String> implements IAcquisitionService{

    private final IAcquisitionRepository acquisitionRepository;

    @Autowired
    public AcquisitionService(IAcquisitionRepository acquisitionRepository) {
        this.acquisitionRepository = acquisitionRepository;
    }

    @Override
    protected IRepository<Acquisition, String> getRepository() {
        return acquisitionRepository;
    }
}
