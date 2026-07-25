/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author Alex
 */
public abstract class WorkOrder {
    
    private static final AtomicInteger ID_SEQUENCE = new AtomicInteger(1000);
    private final int workOrderId;
    private String message;
    private UserAccount sender;
    private UserAccount receiver;
    private final LocalDateTime requestDate;
    private LocalDateTime resolveDate;
    
    private WorkOrderStatus status;
    
    public WorkOrder() {
        // Automatically stamps the creation time when a new order is instantiated
        this.workOrderId = ID_SEQUENCE.getAndIncrement();
        this.requestDate = LocalDateTime.now();
        this.status = WorkOrderStatus.PENDING;
    }
    
    public void setStatus(WorkOrderStatus status) {
        this.status = status;
        
        if (status != null && status.isDone()) {
            this.resolveDate = LocalDateTime.now();
        }
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getWorkOrderId() {
        return workOrderId;
    }

    public UserAccount getSender() {
        return sender;
    }

    public UserAccount getReceiver() {
        return receiver;
    }

    public LocalDateTime getRequestDate() {
        return requestDate;
    }

    public LocalDateTime getResolveDate() {
        return resolveDate;
    }

    public WorkOrderStatus getStatus() {
        return status;
    }

    public void setSender(UserAccount sender) {
        this.sender = sender;
    }

    public void setReceiver(UserAccount receiver) {
        this.receiver = receiver;
    }

    public void setResolveDate(LocalDateTime resolveDate) {
        this.resolveDate = resolveDate;
    }
    
    @Override
    public String toString() {
        return String.valueOf(workOrderId);
    }
}
