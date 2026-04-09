package com.pragma.powerup.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LogsRequestDto {
    private Long idOrder;
    private Long clientId;
    private Long employeeId;
    private LocalDateTime pending;
    private LocalDateTime inPreparation;
    private LocalDateTime ready;
    private LocalDateTime delivered;
}