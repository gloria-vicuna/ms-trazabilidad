package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.api.ILogServicePort;
import com.pragma.powerup.domain.exception.*;
import com.pragma.powerup.domain.model.LogModel;
import com.pragma.powerup.domain.spi.ILogPersistencePort;

import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

public class LogUseCase implements ILogServicePort {

    private final ILogPersistencePort logPersistencePort;

    public LogUseCase(ILogPersistencePort logPersistencePort) {
        this.logPersistencePort = logPersistencePort;
    }

    @Override
    public void saveLog(LogModel logModel, String role) {

        if (logModel.getPending() != null && !role.equals("ROLE_CLIENT")) {
            throw new OnlyClientCanCreateLogException();
        }
        if ((logModel.getInPreparation() != null || logModel.getReady() != null || logModel.getDelivered() != null)
                && !role.equals("ROLE_EMPLOYEE")) {
            throw new OnlyEmployeeCanUpdateLogException();
        }
        logPersistencePort.saveLog(logModel);
    }

    @Override
    public List<LogModel> getRanking() {
        return logPersistencePort.findAllLogs().stream()
                .filter(this::isValidForRanking)
                .collect(Collectors.groupingBy(LogModel::getEmployeeId))
                .entrySet().stream()
                .map(this::calculateEmployeeAverage)
                .sorted(Comparator.comparingDouble(LogModel::getAverageTime))
                .collect(Collectors.toList());
    }

    private boolean isValidForRanking(LogModel log) {
        return log.getEmployeeId() != null && log.getPending() != null && log.getReady() != null;
    }

    private LogModel calculateEmployeeAverage(Map.Entry<Long, List<LogModel>> entry) {
        double average = entry.getValue().stream()
                .mapToLong(log -> Duration.between(log.getPending(), log.getReady()).toMinutes())
                .average().orElse(0.0);

        LogModel ranking = new LogModel();
        ranking.setEmployeeId(entry.getKey());
        ranking.setAverageTime(average);
        return ranking;
    }

    @Override
    public LogModel getOrderTraceability(Long idOrder) {
        LogModel log = logPersistencePort.getLogByOrder(idOrder);
        if (log == null) throw new LogNoFoundException();
        return log;
    }
}