/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package StaffingAgency.Request;

import Business.Network;
import ComplianceEnterprise.ComplianceIntegrationService;
/**
 *
 * @author abhit
 */
   
import StaffingAgency.Enums.RequestStatus;
import StaffingAgency.People.Candidate;
import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicInteger;

public class CandidateSubmission {

    private static final AtomicInteger ID_SEQUENCE =
            new AtomicInteger(4000);

    private final int submissionId;
    private final LocalDate submissionDate;

    private RequestStatus status;
    private String recruiterNotes;
    private String clientFeedback;

    private final Candidate candidate;

    /*
     * Fully qualified name is required because this class is inside
     * StaffingAgency.Request, which also contains another class named
     * StaffingRequest.
     *
     * The current working application uses WorkOrders.StaffingRequest.
     */
    private final WorkOrders.StaffingRequest staffingRequest;

    private ContractorAssignment resultingAssignment;

    public CandidateSubmission(
            Candidate candidate,
            WorkOrders.StaffingRequest staffingRequest,
            String recruiterNotes
    ) {
        if (candidate == null) {
            throw new IllegalArgumentException(
                    "Candidate is required."
            );
        }

        if (staffingRequest == null) {
            throw new IllegalArgumentException(
                    "Staffing request is required."
            );
        }

        if (recruiterNotes == null
                || recruiterNotes.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Recruiter notes are required."
            );
        }

        this.submissionId =
                ID_SEQUENCE.incrementAndGet();

        this.candidate = candidate;
        this.staffingRequest = staffingRequest;
        this.recruiterNotes =
                recruiterNotes.trim();

        this.submissionDate =
                LocalDate.now();

        this.status =
                RequestStatus.SUBMITTED;
    }

    public int getSubmissionId() {
        return submissionId;
    }

    public LocalDate getSubmissionDate() {
        return submissionDate;
    }

    public RequestStatus getStatus() {
        return status;
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

    public Candidate getCandidate() {
        return candidate;
    }

    public WorkOrders.StaffingRequest
            getStaffingRequest() {

        return staffingRequest;
    }

    public ContractorAssignment
            getResultingAssignment() {

        return resultingAssignment;
    }

    /**
     * Sends the newly created submission to the client.
     */
    public void submitToClient() {

        if (status != RequestStatus.SUBMITTED) {
            throw new IllegalStateException(
                    "Only a newly created submission "
                    + "can be sent to the client."
            );
        }

        status = RequestStatus.IN_REVIEW;
    }

    /**
     * Updates the submission status.
     */
    public void updateStatus(
            RequestStatus newStatus
    ) {
        if (newStatus == null) {
            throw new IllegalArgumentException(
                    "Submission status is required."
            );
        }

        status = newStatus;
    }

    /**
     * Adds feedback from the client company.
     */
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

    /**
     * Withdraws a submission that has not already been
     * approved or completed.
     */
    public void withdrawSubmission() {

        if (status == RequestStatus.APPROVED
                || status == RequestStatus.COMPLETED) {

            throw new IllegalStateException(
                    "An approved or completed submission "
                    + "cannot be withdrawn."
            );
        }

        status = RequestStatus.REJECTED;

        if (clientFeedback == null
                || clientFeedback.isBlank()) {

            clientFeedback =
                    "[Withdrawn by recruiter]";

        } else {

            clientFeedback =
                    clientFeedback
                    + " [Withdrawn by recruiter]";
        }
    }

    /**
     * Links the approved submission to the resulting
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
        status = RequestStatus.APPROVED;
    }

    /**
     * Links the client-approved assignment and immediately sends that same
     * object to the Compliance queue.
     *
     * @author janet
     */
    public void linkAssignmentAndSendToCompliance(
            ContractorAssignment assignment, Network network) {
        linkAssignment(assignment);
        // @janet - cross-enterprise handoff using the same assignment object.
        ComplianceIntegrationService.submitForVerification(
                network,
                assignment,
                "Background and Credential Verification",
                "Verify contractor before the assignment start date.");
    }

    @Override
    public String toString() {
        return submissionId
                + " - "
                + candidate.getFullName();
    }
}
