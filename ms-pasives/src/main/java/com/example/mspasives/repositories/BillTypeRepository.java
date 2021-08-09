package com.example.mspasives.repositories;

import com.example.mspasives.models.entities.BillType;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface BillTypeRepository extends IRepository<BillType, String> {
}
