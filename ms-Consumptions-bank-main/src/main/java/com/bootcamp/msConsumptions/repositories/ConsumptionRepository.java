package com.bootcamp.msConsumptions.repositories;

import com.bootcamp.msConsumptions.models.entities.Consumption;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ConsumptionRepository extends ReactiveMongoRepository<Consumption,String> {
}
