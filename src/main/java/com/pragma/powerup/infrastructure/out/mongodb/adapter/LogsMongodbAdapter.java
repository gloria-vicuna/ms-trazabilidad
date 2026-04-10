package com.pragma.powerup.infrastructure.out.mongodb.adapter;

import com.pragma.powerup.domain.model.LogModel;
import com.pragma.powerup.domain.spi.ILogPersistencePort;
import com.pragma.powerup.infrastructure.out.mongodb.entity.LogEntity;
import com.pragma.powerup.infrastructure.out.mongodb.mapper.ILogEntityMapper;
import com.pragma.powerup.infrastructure.out.mongodb.repository.ILogsOrdersRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class LogsMongodbAdapter implements ILogPersistencePort {

    private final ILogsOrdersRepository logsRepository;
    private final ILogEntityMapper logEntityMapper;

    @Override
    public void saveLog(LogModel logModel) {
        LogEntity entity = logsRepository.findByIdOrder(logModel.getIdOrder())
                .map(existing -> updateFields(existing, logModel))
                .orElseGet(() -> logEntityMapper.toDocument(logModel));

        logsRepository.save(entity);
    }

    private LogEntity updateFields(LogEntity existing, LogModel updates) {

        Optional.ofNullable(updates.getPending()).ifPresent(existing::setPending);
        Optional.ofNullable(updates.getInPreparation()).ifPresent(existing::setInPreparation);
        Optional.ofNullable(updates.getReady()).ifPresent(existing::setReady);
        Optional.ofNullable(updates.getDelivered()).ifPresent(existing::setDelivered);
        Optional.ofNullable(updates.getEmployeeId()).ifPresent(existing::setEmployeeId);
        return existing;
    }

    @Override
    public List<LogModel> findAllLogs() {
        return logEntityMapper.toLogList(logsRepository.findAll());
    }

    @Override
    public LogModel getLogByOrder(Long idOrder) {
        return logsRepository.findByIdOrder(idOrder)
                .map(logEntityMapper::toLogOrder)
                .orElse(null);
    }
}