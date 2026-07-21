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
import StaffingAgency.People.Candidate;
import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicInteger;

public class CandidateSubmission {

    private static final AtomicInteger ID_SEQUENCE = new AtomicInteger(4000);

    private final int submissionId;
    private final LocalDate submissionDate;
    private RequestStatus status;
    private String recruiterNotes;
    private String clientFeedback;

    private final Candidate candidate;
    private final StaffingRequest staffingRequest;
    private ContractorAssignment resultingAssignment;

    public CandidateSubmission(Candidate candidate, StaffingRequest staffingRequest, String recruiterNotes) {
        this.submissionId = ID_SEQUENCE.incrementAndGet();
        this.candidate = candidate;
        this.staffingRequest = staffingRequest;
        this.recruiterNotes = recruiterNotes;
        this.submissionDate = LocalDate.now();
        this.status = RequestStatus.SUBMITTED;
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

    public String getClientFeedback() {
        return clientFeedback;
    }

    public Candidate getCandidate() {
        return candidate;
    }

    public StaffingRequest getStaffingRequest() {
        return staffingRequest;
    }

    public ContractorAssignment getResultingAssignment() {
        return resultingAssignment;
    }

    public void submitToClient() {
        this.status = RequestStatus.IN_REVIEW;
    }

    public void updateStatus(RequestStatus status) {
        this.status = status;
    }

    public void addClientFeedback(String feedback) {
        this.clientFeedback = feedback;
    }

    public void withdrawSubmission() {
        this.status = RequestStatus.REJECTED;
        this.clientFeedback = (clientFeedback == null ? "" : clientFeedback + " ") + "[Withdrawn by recruiter]";
    }

    public void linkAssignment(ContractorAssignment assignment) {
        this.resultingAssignment = assignment;
        this.status = RequestStatus.APPROVED;
    }
}

