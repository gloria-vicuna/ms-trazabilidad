package com.pragma.powerup.domain.model;

import java.time.LocalDateTime;

public class LogModel {
    private Long idOrder;
    private Long clientId;
    private Long employeeId;
    private Double averageTime;
    private LocalDateTime pending;
    private LocalDateTime inPreparation;
    private LocalDateTime ready;
    private LocalDateTime delivered;

    public LogModel() {
    }

    public Long getClientId() { return clientId; }
    public void setClientId(Long clientId) { this.clientId = clientId; }

    public Long getEmployeeId() { return employeeId; }
    public void setEmployeeId(Long employeeId) { this.employeeId = employeeId; }

    public Double getAverageTime() { return averageTime; }
    public void setAverageTime(Double averageTime) { this.averageTime = averageTime; }

    public Long getIdOrder() { return idOrder; }
    public void setIdOrder(Long idOrder) { this.idOrder = idOrder; }

    public LocalDateTime getPending() { return pending; }
    public void setPending(LocalDateTime pending) { this.pending = pending; }

    public LocalDateTime getInPreparation() { return inPreparation; }
    public void setInPreparation(LocalDateTime inPreparation) { this.inPreparation = inPreparation; }

    public LocalDateTime getReady() { return ready; }
    public void setReady(LocalDateTime ready) { this.ready = ready; }

    public LocalDateTime getDelivered() { return delivered; }
    public void setDelivered(LocalDateTime delivered) { this.delivered = delivered; }
}