package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.exception.*;
import com.pragma.powerup.domain.model.LogModel;
import com.pragma.powerup.domain.spi.ILogPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LogUseCaseTest {

    private ILogPersistencePort persistencePort;
    private LogUseCase useCase;

    @BeforeEach
    void setUp() {
        persistencePort = mock(ILogPersistencePort.class);
        useCase = new LogUseCase(persistencePort);
    }

    @Test
    void saveLogClientSuccess() {
        LogModel log = new LogModel();
        log.setPending(LocalDateTime.now());

        useCase.saveLog(log, "ROLE_CLIENT");

        verify(persistencePort).saveLog(log);
    }

    @Test
    void saveLogEmployeeUpdateSuccess() {
        LogModel log = new LogModel();
        log.setInPreparation(LocalDateTime.now());

        useCase.saveLog(log, "ROLE_EMPLOYEE");

        verify(persistencePort).saveLog(log);
    }

    @Test
    void saveLogEmployeeUpdateFailWhenNotEmployee() {
        LogModel log = new LogModel();
        log.setReady(LocalDateTime.now());

        assertThrows(OnlyEmployeeCanUpdateLogException.class, () ->
                useCase.saveLog(log, "ROLE_CLIENT")
        );
    }

    @Test
    void getRankingShouldCalculateAverageCorrectly() {

        LocalDateTime start = LocalDateTime.now();

        LogModel log1 = new LogModel();
        log1.setEmployeeId(1L);
        log1.setPending(start);
        log1.setReady(start.plusMinutes(10));

        LogModel log2 = new LogModel();
        log2.setEmployeeId(1L);
        log2.setPending(start);
        log2.setReady(start.plusMinutes(20));

        when(persistencePort.findAllLogs()).thenReturn(Arrays.asList(log1, log2));

        List<LogModel> ranking = useCase.getRanking();


        assertEquals(1, ranking.size());
        assertEquals(15.0, ranking.get(0).getAverageTime());
        assertEquals(1L, ranking.get(0).getEmployeeId());
    }

    @Test
    void getRankingShouldFilterInvalidLogs() {

        LogModel invalidLog = new LogModel();
        invalidLog.setEmployeeId(1L);
        invalidLog.setPending(LocalDateTime.now());
        invalidLog.setReady(null);

        when(persistencePort.findAllLogs()).thenReturn(Arrays.asList(invalidLog));

        List<LogModel> ranking = useCase.getRanking();

        assertTrue(ranking.isEmpty());
    }

    @Test
    void getOrderTraceabilitySuccess() {
        Long orderId = 100L;
        LogModel expectedLog = new LogModel();
        when(persistencePort.getLogByOrder(orderId)).thenReturn(expectedLog);

        LogModel result = useCase.getOrderTraceability(orderId);

        assertNotNull(result);
        verify(persistencePort).getLogByOrder(orderId);
    }

    @Test
    void getOrderTraceabilityNotFound() {
        when(persistencePort.getLogByOrder(anyLong())).thenReturn(null);

        assertThrows(LogNoFoundException.class, () ->
                useCase.getOrderTraceability(1L)
        );
    }
}