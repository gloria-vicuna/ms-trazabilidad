package com.pragma.powerup.application.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RankingResponseDto {
    private Long employeeId;
    private Double averageTime;
}