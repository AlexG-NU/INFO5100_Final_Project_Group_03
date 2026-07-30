/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package StaffingAgency.Request;

import Business.Network;
import ComplianceEnterprise.ComplianceIntegrationService;
import Core.WorkOrder;
import Core.WorkOrderStatus;
import Core.WorkOrders.StaffingReqWorkOrder;
import StaffingAgency.People.Candidate;
import java.time.LocalDate;

/**
 * Candidate submission routed from the Staffing Agency
 * to the Client Human Resources Organization.
 */
public class CandidateSubmission extends WorkOrder {

    private final Candidate candidate;
    private final StaffingReqWorkOrder staffingRequest;

    private String recruiterNotes;
    private String clientFeedback;

    private ContractorAssignment resultingAssignment;

    public CandidateSubmission(
            Candidate candidate,
            StaffingReqWorkOrder staffingRequest,
            String recruiterNotes
    ) {
        super();

        if (candidate == null) {
            throw new IllegalArgumentException(
                    "Candidate is required."
            );
        }

        if (staffingRequest == null) {
            throw new IllegalArgumentException(
                    "Staffing request work order is required."
            );
        }

        if (recruiterNotes == null
                || recruiterNotes.trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Recruiter notes are required."
            );
        }

        this.candidate = candidate;
        this.staffingRequest = staffingRequest;
        this.recruiterNotes = recruiterNotes.trim();

        setMessage(
                "Candidate "
                + candidate.getFullName()
                + " submitted for "
                + staffingRequest.getJobTitle()
        );
    }

    /**
     * Keeps compatibility with the existing submission UI.
     */
    public int getSubmissionId() {
        return getWorkOrderId();
    }

    /**
     * Keeps compatibility with the existing submission UI.
     */
    public LocalDate getSubmissionDate() {
        return getRequestDate().toLocalDate();
    }

    public Candidate getCandidate() {
        return candidate;
    }

    public StaffingReqWorkOrder getStaffingRequest() {
        return staffingRequest;
    }

    public String getRecruiterNotes() {
        return recruiterNotes;
    }

    public void setRecruiterNotes(
            String recruiterNotes
    ) {
        if (recruiterNotes == null
                || recruiterNotes.trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Recruiter notes are required."
            );
        }

        this.recruiterNotes =
                recruiterNotes.trim();
    }

    public String getClientFeedback() {
        return clientFeedback;
    }

    public ContractorAssignment getResultingAssignment() {
        return resultingAssignment;
    }

    /**
     * Marks the work order as sent to Client HR.
     */
    public void submitToClient() {

        if (getStatus() != WorkOrderStatus.PENDING) {
            throw new IllegalStateException(
                    "Only a pending submission can be sent "
                    + "to the client."
            );
        }

        setStatus(
                WorkOrderStatus.UNDER_REVIEW
        );
    }

    public void updateStatus(
            WorkOrderStatus newStatus
    ) {
        if (newStatus == null) {
            throw new IllegalArgumentException(
                    "Submission status is required."
            );
        }

        setStatus(newStatus);
    }

    public void addClientFeedback(
            String feedback
    ) {
        if (feedback == null
                || feedback.trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Client feedback cannot be blank."
            );
        }

        clientFeedback = feedback.trim();
    }

    public void withdrawSubmission() {

        if (getStatus() == WorkOrderStatus.APPROVED
                || getStatus()
                == WorkOrderStatus.COMPLETED) {

            throw new IllegalStateException(
                    "An approved or completed submission "
                    + "cannot be withdrawn."
            );
        }

        setStatus(
                WorkOrderStatus.CANCELLED
        );

        if (clientFeedback == null
                || clientFeedback.isBlank()) {

            clientFeedback =
                    "[Withdrawn by recruiter]";

        } else {

            clientFeedback +=
                    " [Withdrawn by recruiter]";
        }
    }

    /**
     * Links the client-approved submission to the
     * contractor assignment.
     */
    public void linkAssignment(
            ContractorAssignment assignment
    ) {
        if (assignment == null) {
            throw new IllegalArgumentException(
                    "Contractor assignment is required."
            );
        }

        resultingAssignment = assignment;

        setStatus(
                WorkOrderStatus.APPROVED
        );
    }

    public void linkAssignmentAndSendToCompliance(
            ContractorAssignment assignment,
            Network network
    ) {
        linkAssignment(assignment);

        ComplianceIntegrationService
                .submitForVerification(
                        network,
                        assignment,
                        "Background and Credential Verification",
                        "Verify contractor before the "
                        + "assignment start date."
                );
    }

    @Override
    public String toString() {
        return getWorkOrderId()
                + " - "
                + candidate.getFullName();
    }
}