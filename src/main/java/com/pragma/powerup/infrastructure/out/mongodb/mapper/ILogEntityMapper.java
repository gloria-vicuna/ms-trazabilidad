package com.pragma.powerup.infrastructure.out.mongodb.mapper;

import com.pragma.powerup.domain.model.LogModel;
import com.pragma.powerup.infrastructure.out.mongodb.entity.LogEntity;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ILogEntityMapper {

    LogEntity toDocument(LogModel logModel);

    LogModel toLogOrder(LogEntity logEntity);

    List<LogModel> toLogList(List<LogEntity> logEntityList);
}