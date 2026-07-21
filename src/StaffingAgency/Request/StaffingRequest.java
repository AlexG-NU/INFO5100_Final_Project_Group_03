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
import StaffingAgency.Staff.Recruiter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class StaffingRequest {

    private static final AtomicInteger ID_SEQUENCE = new AtomicInteger(3000);

    private final int requestId;
    private String jobTitle;
    private String description;
    private String requiredSkills;
    private int numberOfPositions;
    private final LocalDate startDate;
    private RequestStatus status;
    private final LocalDate submittedDate;
    private Recruiter assignedRecruiter;
    private final List<CandidateSubmission> submissions = new ArrayList<>();

    public StaffingRequest(String jobTitle, String description, String requiredSkills,
                            int numberOfPositions, LocalDate startDate) {
        this.requestId = ID_SEQUENCE.incrementAndGet();
        setJobTitle(jobTitle);
        this.description = description;
        this.requiredSkills = requiredSkills;
        setNumberOfPositions(numberOfPositions);
        this.startDate = startDate;
        this.status = RequestStatus.SUBMITTED;
        this.submittedDate = LocalDate.now();
    }

    public int getRequestId() {
        return requestId;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        if (jobTitle == null || jobTitle.isBlank()) {
            throw new IllegalArgumentException("jobTitle cannot be blank");
        }
        this.jobTitle = jobTitle;
    }

    public String getDescription() {
        return description;
    }

    public String getRequiredSkills() {
        return requiredSkills;
    }

    public int getNumberOfPositions() {
        return numberOfPositions;
    }

    public void setNumberOfPositions(int numberOfPositions) {
        if (numberOfPositions <= 0) {
            throw new IllegalArgumentException("numberOfPositions must be at least 1");
        }
        this.numberOfPositions = numberOfPositions;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public RequestStatus getStatus() {
        return status;
    }

    public LocalDate getSubmittedDate() {
        return submittedDate;
    }

    public Recruiter getAssignedRecruiter() {
        return assignedRecruiter;
    }

    public void assignRecruiter(Recruiter recruiter) {
        this.assignedRecruiter = recruiter;
    }

    public void updateStatus(RequestStatus status) {
        this.status = status;
    }

    public void closeRequest() {
        this.status = RequestStatus.COMPLETED;
    }

    public void addSubmission(CandidateSubmission submission) {
        submissions.add(submission);
    }

    public List<CandidateSubmission> getSubmissions() {
        return List.copyOf(submissions);
    }
}