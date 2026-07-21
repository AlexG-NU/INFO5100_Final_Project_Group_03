/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author abhit
 */
 package StaffingAgency.Request;

import StaffingAgency.Enums.AssignmentStatus;
import StaffingAgency.People.Contractor;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ContractorAssignment {

    private static final AtomicInteger ID_SEQUENCE = new AtomicInteger(5000);

    private final int assignmentId;
    private final LocalDate startDate;
    private LocalDate endDate;
    private AssignmentStatus status;

    private final Contractor contractor;
    private Contract contract; // set once a Contract is created for this assignment
    private final List<CredentialVerificationRequest> verificationRequests = new ArrayList<>();

    public ContractorAssignment(Contractor contractor, LocalDate startDate) {
        this.assignmentId = ID_SEQUENCE.incrementAndGet();
        this.contractor = contractor;
        this.startDate = startDate;
        this.status = AssignmentStatus.PENDING;
    }

    public int getAssignmentId() {
        return assignmentId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public AssignmentStatus getStatus() {
        return status;
    }

    public Contractor getContractor() {
        return contractor;
    }

    public Contract getContract() {
        return contract;
    }

    /** Called by Contract's constructor - links this assignment to its governing contract. */
    public void assignContract(Contract contract) {
        this.contract = contract;
    }

    public void activateAssignment() {
        this.status = AssignmentStatus.ACTIVE;
    }

    public void completeAssignment() {
        this.status = AssignmentStatus.COMPLETED;
    }

    public void terminateAssignment() {
        this.status = AssignmentStatus.TERMINATED;
    }

    public void extendAssignment(LocalDate newEndDate) {
        if (endDate != null && newEndDate.isBefore(endDate)) {
            throw new IllegalArgumentException("newEndDate cannot be before the current endDate");
        }
        this.endDate = newEndDate;
    }

    public void addVerificationRequest(CredentialVerificationRequest request) {
        verificationRequests.add(request);
    }

    public List<CredentialVerificationRequest> getVerificationRequests() {
        return List.copyOf(verificationRequests);
    }
}

