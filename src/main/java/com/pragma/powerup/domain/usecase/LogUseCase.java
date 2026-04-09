package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.api.ILogServicePort;
import com.pragma.powerup.domain.exception.LogNoFoundException;
import com.pragma.powerup.domain.model.LogModel;
import com.pragma.powerup.domain.spi.ILogPersistencePort;

import java.time.Duration;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


public class LogUseCase implements ILogServicePort {

    private final ILogPersistencePort logPersistencePort;

    public LogUseCase(ILogPersistencePort logPersistencePort) {
        this.logPersistencePort = logPersistencePort;
    }

    @Override
    public void saveLog(LogModel logModel) {

        logPersistencePort.saveLog(logModel);
    }

    @Override
    public LogModel getOrderTraceability(Long idOrder) {
        LogModel log = logPersistencePort.getLogByOrder(idOrder);
        if (log == null) throw new LogNoFoundException();
        return log;
    }

    @Override
    public Long totalTime(Long idOrder) {
        LogModel log = getOrderTraceability(idOrder);
        if (log.getPending() == null || log.getDelivered() == null) return 0L;
        return Duration.between(log.getPending(), log.getDelivered()).toMinutes();
    }

    @Override
    public List<LogModel> getRanking() {
        List<LogModel> allLogs = logPersistencePort.findAllLogs();

        return allLogs.stream()
                .filter(log -> log != null &&
                        log.getEmployeeId() != null &&
                        log.getPending() != null &&
                        log.getReady() != null)
                .collect(Collectors.groupingBy(LogModel::getEmployeeId))
                .entrySet().stream()
                .map(entry -> {
                    Long employeeId = entry.getKey();
                    List<LogModel> employeeLogs = entry.getValue();

                    double averageTime = employeeLogs.stream()
                            .mapToLong(log -> Duration.between(log.getPending(), log.getReady()).toMinutes())
                            .average()
                            .orElse(0.0);

                    LogModel rankingModel = new LogModel();
                    rankingModel.setEmployeeId(employeeId);
                    rankingModel.setAverageTime(averageTime);
                    return rankingModel;
                })
                .sorted(Comparator.comparingDouble(LogModel::getAverageTime))
                .collect(Collectors.toList());
    }
}