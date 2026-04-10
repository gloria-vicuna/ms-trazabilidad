package com.pragma.powerup.application.handler.impl;

import com.pragma.powerup.application.dto.request.LogsRequestDto;
import com.pragma.powerup.application.dto.response.RankingResponseDto;
import com.pragma.powerup.application.dto.response.LogsResponseDto;
import com.pragma.powerup.application.handler.ILogsHandler;
import com.pragma.powerup.application.mapper.LogDtoMapper;
import com.pragma.powerup.domain.api.ILogServicePort;
import com.pragma.powerup.domain.model.LogModel;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LogsHandler implements ILogsHandler {

    private final ILogServicePort logServicePort;
    private final LogDtoMapper logDtoMapper;

    @Override
    public void saveLog(LogsRequestDto logDto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String role = auth.getAuthorities().iterator().next().getAuthority();
        Long userId = Long.parseLong(auth.getName());

        LogModel model = logDtoMapper.toLog(logDto);


        if (role.equals("ROLE_CLIENT")) model.setClientId(userId);
        if (role.equals("ROLE_EMPLOYEE")) model.setEmployeeId(userId);

        logServicePort.saveLog(model, role);
    }

    @Override
    public LogsResponseDto getOrderTraceability(Long idOrder) {
        LogModel logModel = logServicePort.getOrderTraceability(idOrder);
        return new LogsResponseDto(idOrder, logDtoMapper.toResponseList(logModel));
    }

    @Override
    public List<RankingResponseDto> getRanking() {
        return logServicePort.getRanking().stream()
                .map(m -> new RankingResponseDto(m.getEmployeeId(), m.getAverageTime()))
                .collect(Collectors.toList());
    }
}