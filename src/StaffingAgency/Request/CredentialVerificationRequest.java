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

public class CredentialVerificationRequest {

    private static final AtomicInteger ID_SEQUENCE = new AtomicInteger(8000);

    private final int verificationRequestId;
    private final String verificationType;
    private final LocalDate requestDate;
    private String notes;
    private RequestStatus status;

    private final ContractorAssignment assignment;

    public CredentialVerificationRequest(ContractorAssignment assignment, String verificationType, String notes) {
        this.verificationRequestId = ID_SEQUENCE.incrementAndGet();
        this.assignment = assignment;
        this.verificationType = verificationType;
        this.notes = notes;
        this.requestDate = LocalDate.now();
        this.status = RequestStatus.SUBMITTED;
        assignment.addVerificationRequest(this);
    }

    public int getVerificationRequestId() {
        return verificationRequestId;
    }

    public String getVerificationType() {
        return verificationType;
    }

    public LocalDate getRequestDate() {
        return requestDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public RequestStatus getStatus() {
        return status;
    }

    public ContractorAssignment getAssignment() {
        return assignment;
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

    /**
     * Applies the verification result to the assignment once approved.
     * NOTE: Compliance (Janet's part) will likely add result-specific fields
     * (verifiedDate, verifiedBy, findings, etc.) to this class once her model
     * is finalized - this method is the integration point where that data
     * should eventually get read.
     */
    public void applyVerification() {
        if (status != RequestStatus.APPROVED) {
            throw new IllegalStateException("Cannot apply a verification that has not been approved");
        }
        this.status = RequestStatus.COMPLETED;
    }
}

