package com.pragma.powerup.domain.api;

import com.pragma.powerup.domain.model.LogModel;

import java.util.List;

public interface ILogServicePort {
    void saveLog(LogModel logModel);

    LogModel getOrderTraceability(Long idOrder);

    Long totalTime(Long idOrder);

    List<LogModel> getRanking();
}
