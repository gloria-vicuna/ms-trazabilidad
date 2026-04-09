package com.pragma.powerup.application.mapper;

import com.pragma.powerup.application.dto.request.LogsRequestDto;
import com.pragma.powerup.application.dto.response.LogsResponseDto;
import com.pragma.powerup.application.dto.response.OrderHistoryResponseDto;
import com.pragma.powerup.domain.model.LogModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;


import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface LogDtoMapper {

    LogModel toLog(LogsRequestDto logDto);

    LogsResponseDto toEfficiencyDto(LogModel logModel);

    List<LogsResponseDto> toResponseListFromModels(List<LogModel> logList);

    default List<OrderHistoryResponseDto> toResponseList(LogModel log) {
        List<OrderHistoryResponseDto> history = new ArrayList<>();
        if (log.getPending() != null) history.add(new OrderHistoryResponseDto("PENDING", log.getPending()));
        if (log.getInPreparation() != null) history.add(new OrderHistoryResponseDto("IN_PREPARATION", log.getInPreparation()));
        if (log.getReady() != null) history.add(new OrderHistoryResponseDto("READY", log.getReady()));
        if (log.getDelivered() != null) history.add(new OrderHistoryResponseDto("DELIVERED", log.getDelivered()));
        return history;
    }
}