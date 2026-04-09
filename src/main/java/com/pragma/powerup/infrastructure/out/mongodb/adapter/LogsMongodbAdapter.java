package com.pragma.powerup.infrastructure.out.mongodb.adapter;

import com.pragma.powerup.domain.model.LogModel;
import com.pragma.powerup.domain.spi.ILogPersistencePort;
import com.pragma.powerup.infrastructure.out.mongodb.mapper.ILogEntityMapper;
import com.pragma.powerup.infrastructure.out.mongodb.repository.ILogsOrdersRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class LogsMongodbAdapter implements ILogPersistencePort {

    private final ILogsOrdersRepository logsRepository;
    private final ILogEntityMapper logEntityMapper;

    @Override
    public void saveLog(LogModel logModel) {
        logsRepository.findByIdOrder(logModel.getIdOrder()).ifPresentOrElse(
                logEntity -> {
                    if (logModel.getPending() != null) logEntity.setPending(logModel.getPending());
                    if (logModel.getInPreparation() != null) logEntity.setInPreparation(logModel.getInPreparation());
                    if (logModel.getReady() != null) logEntity.setReady(logModel.getReady());
                    if (logModel.getDelivered() != null) logEntity.setDelivered(logModel.getDelivered());
                    if (logModel.getEmployeeId() != null) logEntity.setEmployeeId(logModel.getEmployeeId());

                    logsRepository.save(logEntity);
                },
                () -> logsRepository.save(logEntityMapper.toDocument(logModel))
        );
    }

    @Override
    public LogModel getLogByOrder(Long idOrder) {
        return logsRepository.findByIdOrder(idOrder)
                .map(logEntityMapper::toLogOrder)
                .orElse(null);
    }

    @Override
    public List<LogModel> findAllLogs() {
        return logEntityMapper.toLogList(logsRepository.findAll());
    }

    @Override
    public void updateLog(LogModel logModel) {
        logsRepository.findByIdOrder(logModel.getIdOrder()).ifPresent(logEntity -> {
            if (logModel.getPending() != null) logEntity.setPending(logModel.getPending());
            if (logModel.getInPreparation() != null) logEntity.setInPreparation(logModel.getInPreparation());
            if (logModel.getReady() != null) logEntity.setReady(logModel.getReady());
            if (logModel.getDelivered() != null) logEntity.setDelivered(logModel.getDelivered());
            if (logModel.getEmployeeId() != null) logEntity.setEmployeeId(logModel.getEmployeeId());

            logsRepository.save(logEntity);
        });
    }
}