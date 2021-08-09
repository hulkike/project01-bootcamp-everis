package com.example.mspasives.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface Movement extends ReactiveMongoRepository<Movement, String> {
}
