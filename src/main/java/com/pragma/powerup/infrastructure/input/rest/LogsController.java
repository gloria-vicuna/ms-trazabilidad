package com.pragma.powerup.infrastructure.input.rest;

import com.pragma.powerup.application.dto.response.RankingResponseDto;
import com.pragma.powerup.application.handler.ILogsHandler;
import com.pragma.powerup.application.dto.request.LogsRequestDto;
import com.pragma.powerup.application.dto.response.LogsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/traceability")
@RequiredArgsConstructor
public class LogsController {

    private final ILogsHandler logHandler;

    @PostMapping("/log")
    public ResponseEntity<Void> createLog(@RequestBody LogsRequestDto logDto) {
        logHandler.saveLog(logDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/history/{idOrder}")
    public ResponseEntity<LogsResponseDto> getOrderHistory(@PathVariable Long idOrder) {
        return ResponseEntity.ok(logHandler.getOrderTraceability(idOrder));
    }

    @GetMapping("/ranking")
    public ResponseEntity<List<RankingResponseDto>> getEmployeeRanking() {
        return ResponseEntity.ok(logHandler.getRanking());
    }
}