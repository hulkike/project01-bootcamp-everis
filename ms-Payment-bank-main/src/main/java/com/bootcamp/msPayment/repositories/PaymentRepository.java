package com.bootcamp.msPayment.repositories;

import com.bootcamp.msPayment.models.entities.Payment;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface PaymentRepository extends ReactiveMongoRepository<Payment,String> {
}
