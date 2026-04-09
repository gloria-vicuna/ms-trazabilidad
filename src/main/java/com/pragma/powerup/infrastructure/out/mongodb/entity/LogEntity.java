package com.pragma.powerup.infrastructure.out.mongodb.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Document(collection = "logs-orders")
public class LogEntity {
    @Id
    private String  id;
    private Long clientId;
    private Long idOrder;
    private Long employeeId;
    private LocalDateTime pending;
    private LocalDateTime inPreparation;
    private LocalDateTime ready;
    private LocalDateTime delivered;
}