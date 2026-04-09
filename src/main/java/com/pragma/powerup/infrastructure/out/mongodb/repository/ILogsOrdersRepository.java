package com.pragma.powerup.infrastructure.out.mongodb.repository;

import com.pragma.powerup.infrastructure.out.mongodb.entity.LogEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ILogsOrdersRepository extends MongoRepository<LogEntity,Long> {
    Optional<LogEntity> findByIdOrder(Long idOrder);
}