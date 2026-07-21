/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package StaffingAgency.Request;

/**
 *
 * @author abhit
 */
    

import StaffingAgency.Enums.RequestStatus;
import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicInteger;

public class ContractExtensionRequest {

    private static final AtomicInteger ID_SEQUENCE = new AtomicInteger(7000);

    private final int extensionRequestId;
    private final LocalDate currentEndDate;
    private final LocalDate requestedEndDate;
    private final String reason;
    private final LocalDate requestDate;
    private RequestStatus status;

    private final Contract contract;

    public ContractExtensionRequest(Contract contract, LocalDate requestedEndDate, String reason) {
        this.extensionRequestId = ID_SEQUENCE.incrementAndGet();
        this.contract = contract;
        this.currentEndDate = contract.getEndDate();
        if (requestedEndDate.isBefore(this.currentEndDate)) {
            throw new IllegalArgumentException("requestedEndDate cannot be before the current end date");
        }
        this.requestedEndDate = requestedEndDate;
        this.reason = reason;
        this.requestDate = LocalDate.now();
        this.status = RequestStatus.SUBMITTED;
        contract.addExtensionRequest(this);
    }

    public int getExtensionRequestId() {
        return extensionRequestId;
    }

    public LocalDate getCurrentEndDate() {
        return currentEndDate;
    }

    public LocalDate getRequestedEndDate() {
        return requestedEndDate;
    }

    public String getReason() {
        return reason;
    }

    public LocalDate getRequestDate() {
        return requestDate;
    }

    public RequestStatus getStatus() {
        return status;
    }

    public Contract getContract() {
        return contract;
    }

    public void submitRequest() {
        this.status = RequestStatus.SUBMITTED;
    }

    public void approveRequest() {
        this.status = RequestStatus.APPROVED;
    }

    public void rejectRequest() {
        this.status = RequestStatus.REJECTED;
    }

    /** Pushes the requested end date onto the governing Contract, once approved. */
    public void applyExtension() {
        if (status != RequestStatus.APPROVED) {
            throw new IllegalStateException("Cannot apply an extension that has not been approved");
        }
        contract.extendContract(requestedEndDate);
        this.status = RequestStatus.COMPLETED;
    }
}
    
