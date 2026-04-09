package com.pragma.powerup.application.handler;

import com.pragma.powerup.application.dto.request.LogsRequestDto;
import com.pragma.powerup.application.dto.response.LogsResponseDto;
import com.pragma.powerup.application.dto.response.OrderHistoryResponseDto;
import com.pragma.powerup.application.dto.response.RankingResponseDto;

import java.util.List;

public interface ILogsHandler {

    void saveLog(LogsRequestDto pedidoDto);

    LogsResponseDto getOrderTraceability(Long idOrder);

    List<RankingResponseDto> getRanking();
}