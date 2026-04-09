package com.pragma.powerup.application.handler.impl;

import com.pragma.powerup.application.dto.request.LogsRequestDto;
import com.pragma.powerup.application.dto.response.RankingResponseDto;
import com.pragma.powerup.application.dto.response.LogsResponseDto;
import com.pragma.powerup.application.handler.ILogsHandler;
import com.pragma.powerup.application.mapper.LogDtoMapper;
import com.pragma.powerup.domain.api.ILogServicePort;
import com.pragma.powerup.domain.exception.NotOwnerOfOrderException;
import com.pragma.powerup.domain.exception.OnlyClientCanCreateLogException;
import com.pragma.powerup.domain.exception.OnlyEmployeeCanUpdateLogException;
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

        Long userIdFromToken = Long.parseLong(auth.getName());

        if (logDto.getPending() != null && !role.equals("ROLE_CLIENT")) {
            throw new OnlyClientCanCreateLogException();
        }

        if ((logDto.getInPreparation() != null || logDto.getReady() != null || logDto.getDelivered() != null)
                && !role.equals("ROLE_EMPLOYEE")) {
            throw new OnlyEmployeeCanUpdateLogException();
        }

        if (role.equals("ROLE_CLIENT")) {
            logDto.setClientId(userIdFromToken);
        }

        if (role.equals("ROLE_EMPLOYEE")) {
            logDto.setEmployeeId(userIdFromToken);
        }

        logServicePort.saveLog(logDtoMapper.toLog(logDto));
    }

    @Override
    public LogsResponseDto getOrderTraceability(Long idOrder) {
        LogModel logModel = logServicePort.getOrderTraceability(idOrder);

        Long clientIdFromToken = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getName());
        if (!logModel.getClientId().equals(clientIdFromToken)) {
            throw new NotOwnerOfOrderException();
        }

        LogsResponseDto response = new LogsResponseDto();
        response.setIdOrder(idOrder);
        response.setHistory(logDtoMapper.toResponseList(logModel));

        return response;
    }

    @Override
    public List<RankingResponseDto> getRanking() {
        List<LogModel> rankingModels = logServicePort.getRanking();

        return rankingModels.stream()
                .map(model -> new RankingResponseDto(
                        model.getEmployeeId(),
                        model.getAverageTime()))
                .collect(Collectors.toList());
    }
}