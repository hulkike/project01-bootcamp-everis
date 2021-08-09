package com.example.mspasives.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface Commission extends ReactiveMongoRepository<Commission, String> {
}
