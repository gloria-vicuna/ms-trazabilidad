package com.pragma.powerup.domain.spi;

import com.pragma.powerup.domain.model.LogModel;

import java.util.List;

public interface ILogPersistencePort {

    void saveLog(LogModel logModel);

    LogModel getLogByOrder(Long idOrder);

    List<LogModel> findAllLogs();

}