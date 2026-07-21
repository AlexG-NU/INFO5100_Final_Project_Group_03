/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package StaffingAgency.Request;

/**
 *
 * @author abhit
 */
  

import StaffingAgency.Enums.ContractStatus;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Contract {

    private static final AtomicInteger ID_SEQUENCE = new AtomicInteger(6000);

    private final int contractId;
    private final LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal payRate;
    private BigDecimal billRate;
    private ContractStatus status;
    private final List<ContractExtensionRequest> extensionRequests = new ArrayList<>();

    public Contract(ContractorAssignment assignment, LocalDate startDate, LocalDate endDate,
                     BigDecimal payRate, BigDecimal billRate) {
        this.contractId = ID_SEQUENCE.incrementAndGet();
        this.startDate = startDate;
        this.endDate = endDate;
        setPayRate(payRate);
        setBillRate(billRate);
        this.status = ContractStatus.ACTIVE;
        assignment.assignContract(this);
    }

    public int getContractId() {
        return contractId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public BigDecimal getPayRate() {
        return payRate;
    }

    public void setPayRate(BigDecimal payRate) {
        if (payRate == null || payRate.signum() < 0) {
            throw new IllegalArgumentException("payRate must be a non-negative amount");
        }
        this.payRate = payRate;
    }

    public BigDecimal getBillRate() {
        return billRate;
    }

    public void setBillRate(BigDecimal billRate) {
        if (billRate == null || billRate.signum() < 0) {
            throw new IllegalArgumentException("billRate must be a non-negative amount");
        }
        if (payRate != null && billRate.compareTo(payRate) < 0) {
            throw new IllegalArgumentException("billRate cannot be lower than payRate");
        }
        this.billRate = billRate;
    }

    public ContractStatus getStatus() {
        return status;
    }

    public void activateContract() {
        this.status = ContractStatus.ACTIVE;
    }

    public void extendContract(LocalDate newEndDate) {
        if (newEndDate.isBefore(this.endDate)) {
            throw new IllegalArgumentException("newEndDate cannot be before the current endDate");
        }
        this.endDate = newEndDate;
        this.status = ContractStatus.EXTENDED;
    }

    public void terminateContract() {
        this.status = ContractStatus.TERMINATED;
    }

    public void addExtensionRequest(ContractExtensionRequest request) {
        extensionRequests.add(request);
    }

    public List<ContractExtensionRequest> getExtensionRequests() {
        return List.copyOf(extensionRequests);
    }
}

